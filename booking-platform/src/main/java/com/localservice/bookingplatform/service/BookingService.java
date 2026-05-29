package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateBookingRequest;
import com.localservice.bookingplatform.dto.BookingResponse;
import com.localservice.bookingplatform.enums.BookingStatus;
import com.localservice.bookingplatform.model.*;
import com.localservice.bookingplatform.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final ServiceProviderRepository providerRepository;

    public BookingService(BookingRepository bookingRepository,
                          TimeSlotRepository timeSlotRepository,
                          UserRepository userRepository,
                          ServiceProviderRepository providerRepository) {
        this.bookingRepository = bookingRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    public BookingResponse createBooking(CreateBookingRequest request) {
        String email = getCurrentUserEmail();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        TimeSlot slot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new RuntimeException("Time slot not found"));


        if (slot.getIsBooked()) {
            throw new RuntimeException("This slot is already booked");
        }

        ServiceProvider provider = slot.getProvider();
        Double totalAmount = provider.getHourlyRate(); // Simple calculation


        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setProvider(provider);
        booking.setTimeSlot(slot);
        booking.setStatus(BookingStatus.PENDING);
        booking.setTotalAmount(totalAmount);
        booking.setNotes(request.getNotes());
        booking.setCreatedAt(LocalDateTime.now());

        // Mark slot as booked
        slot.setIsBooked(true);
        timeSlotRepository.save(slot);

        Booking saved = bookingRepository.save(booking);
        return convertToResponse(saved);
    }


    public List<BookingResponse> getMyBookings() {
        String email = getCurrentUserEmail();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByCustomer(customer)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
        return convertToResponse(booking);
    }

    private BookingResponse convertToResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getCustomer().getEmail(),
                booking.getProvider().getBusinessName(),
                booking.getTimeSlot().getSlotDate().toString(),
                booking.getTimeSlot().getStartTime().toString(),
                booking.getStatus().name(),
                booking.getTotalAmount(),
                booking.getNotes(),
                booking.getCreatedAt()
        );
    }
    public List<BookingResponse> getProviderBookings() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Provider profile not found"));

        return bookingRepository.findByProvider(provider)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}