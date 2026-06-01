package com.localservice.bookingplatform;

import com.localservice.bookingplatform.dto.BookingResponse;
import com.localservice.bookingplatform.dto.CreateBookingRequest;
import com.localservice.bookingplatform.enums.BookingStatus;
import com.localservice.bookingplatform.enums.Role;
import com.localservice.bookingplatform.model.*;
import com.localservice.bookingplatform.repository.*;
import com.localservice.bookingplatform.service.BookingService;
import com.localservice.bookingplatform.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private TimeSlotRepository timeSlotRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceProviderRepository providerRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private BookingService bookingService;

    private User customer;
    private ServiceProvider provider;
    private TimeSlot timeSlot;
    private CreateBookingRequest request;

    @BeforeEach
    void setUp() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getName()).thenReturn("customer@gmail.com");
        SecurityContextHolder.setContext(securityContext);

        customer = new User();
        customer.setId(1L);
        customer.setEmail("customer@gmail.com");
        customer.setFullName("Test Customer");
        customer.setRole(Role.CUSTOMER);

        User providerUser = new User();
        providerUser.setId(2L);
        providerUser.setEmail("provider@gmail.com");
        providerUser.setFullName("Test Provider");

        provider = new ServiceProvider();
        provider.setId(1L);
        provider.setBusinessName("Test Business");
        provider.setHourlyRate(500.0);
        provider.setUser(providerUser);

        timeSlot = new TimeSlot();
        timeSlot.setSlotDate(LocalDate.of(2026, 6, 15));
        timeSlot.setStartTime(LocalTime.of(10, 0));
        timeSlot.setEndTime(LocalTime.of(11, 0));
        timeSlot.setIsBooked(false);
        timeSlot.setProvider(provider);

        request = new CreateBookingRequest();
        request.setTimeSlotId(1L);
        request.setNotes("Test notes");
    }

    @Test
    void createBooking_ShouldReturnBooking_WhenSlotAvailable() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(timeSlot));

        Booking savedBooking = new Booking();
        savedBooking.setId(1L);
        savedBooking.setCustomer(customer);
        savedBooking.setProvider(provider);
        savedBooking.setTimeSlot(timeSlot);
        savedBooking.setStatus(BookingStatus.PENDING);
        savedBooking.setTotalAmount(500.0);
        savedBooking.setNotes("Test notes");
        savedBooking.setCreatedAt(LocalDateTime.now());

        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        BookingResponse response = bookingService.createBooking(request);

        assertNotNull(response);
        assertEquals(BookingStatus.PENDING.name(), response.getStatus());
        assertEquals(500.0, response.getTotalAmount());
        verify(timeSlotRepository, times(1)).save(any(TimeSlot.class));
    }

    @Test
    void createBooking_ShouldThrowException_WhenSlotAlreadyBooked() {
        timeSlot.setIsBooked(true);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.of(timeSlot));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookingService.createBooking(request));

        assertEquals("This slot is already booked", exception.getMessage());
        verify(bookingRepository, never()).save(any(Booking.class));
    }

    @Test
    void createBooking_ShouldThrowException_WhenSlotNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));
        when(timeSlotRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookingService.createBooking(request));

        assertEquals("Time slot not found", exception.getMessage());
    }
}