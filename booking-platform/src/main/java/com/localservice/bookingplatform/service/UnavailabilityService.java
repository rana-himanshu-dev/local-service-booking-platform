package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateUnavailabilityRequest;
import com.localservice.bookingplatform.dto.UnavailabilityResponse;
import com.localservice.bookingplatform.model.*;
import com.localservice.bookingplatform.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UnavailabilityService {

    private final UnavailabilityRepository unavailabilityRepository;
    private final ServiceProviderRepository providerRepository;
    private final UserRepository userRepository;

    public UnavailabilityService(UnavailabilityRepository unavailabilityRepository,
                                 ServiceProviderRepository providerRepository,
                                 UserRepository userRepository) {
        this.unavailabilityRepository = unavailabilityRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    public UnavailabilityResponse createUnavailability(
            CreateUnavailabilityRequest request) {

        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServiceProvider provider = providerRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Provider profile not found"));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new RuntimeException("End date must be after start date");
        }

        Unavailability unavailability = new Unavailability();
        unavailability.setProvider(provider);
        unavailability.setStartDate(request.getStartDate());
        unavailability.setEndDate(request.getEndDate());
        unavailability.setReason(request.getReason());
        unavailability.setCreatedAt(LocalDateTime.now());

        Unavailability saved = unavailabilityRepository.save(unavailability);
        return convertToResponse(saved);
    }


    public List<UnavailabilityResponse> getProviderUnavailability(Long providerId) {
        return unavailabilityRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public void deleteUnavailability(Long unavailabilityId) {
        Unavailability unavailability = unavailabilityRepository.findById(unavailabilityId)
                .orElseThrow(() -> new RuntimeException("Unavailability not found"));
        unavailabilityRepository.delete(unavailability);
    }

    private UnavailabilityResponse convertToResponse(Unavailability unavailability) {
        return new UnavailabilityResponse(
                unavailability.getId(),
                unavailability.getStartDate(),
                unavailability.getEndDate(),
                unavailability.getReason(),
                unavailability.getCreatedAt()
        );
    }
}