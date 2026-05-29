package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateTimeSlotRequest;
import com.localservice.bookingplatform.dto.TimeSlotResponse;
import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.TimeSlot;
import com.localservice.bookingplatform.model.User;
import com.localservice.bookingplatform.repository.ServiceProviderRepository;
import com.localservice.bookingplatform.repository.TimeSlotRepository;
import com.localservice.bookingplatform.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final ServiceProviderRepository providerRepository;
    private final UserRepository userRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository,
                           ServiceProviderRepository providerRepository,
                           UserRepository userRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    public TimeSlotResponse createTimeSlot(CreateTimeSlotRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException(
                        "Provider profile not found. Create profile first."));

        TimeSlot slot = new TimeSlot();
        slot.setProvider(provider);
        slot.setSlotDate(request.getSlotDate());
        slot.setStartTime(request.getStartTime());
        slot.setEndTime(request.getEndTime());
        slot.setIsBooked(false);

        TimeSlot saved = timeSlotRepository.save(slot);
        return convertToResponse(saved);
    }

    public List<TimeSlotResponse> getAvailableSlots(Long providerId) {
        return timeSlotRepository.findByProviderIdAndIsBooked(providerId, false)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    public List<TimeSlotResponse> getProviderSlots(Long providerId) {
        return timeSlotRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void deleteSlot(Long slotId) {
        TimeSlot slot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found: " + slotId));

        timeSlotRepository.delete(slot);
    }

    private TimeSlotResponse convertToResponse(TimeSlot slot) {
        return new TimeSlotResponse(
                slot.getId(),
                slot.getSlotDate(),
                slot.getStartTime(),
                slot.getEndTime(),
                slot.getIsBooked()
        );
    }
}