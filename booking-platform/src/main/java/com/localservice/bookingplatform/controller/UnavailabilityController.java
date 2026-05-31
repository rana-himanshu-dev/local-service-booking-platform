package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreateUnavailabilityRequest;
import com.localservice.bookingplatform.dto.UnavailabilityResponse;
import com.localservice.bookingplatform.service.UnavailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/unavailability")
public class UnavailabilityController {

    private final UnavailabilityService unavailabilityService;

    public UnavailabilityController(UnavailabilityService unavailabilityService) {
        this.unavailabilityService = unavailabilityService;
    }

    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<UnavailabilityResponse> createUnavailability(
            @Valid @RequestBody CreateUnavailabilityRequest request) {
        UnavailabilityResponse response = unavailabilityService.createUnavailability(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<UnavailabilityResponse>> getUnavailability(
            @PathVariable Long providerId) {
        List<UnavailabilityResponse> blocks = unavailabilityService.getProviderUnavailability(providerId);
        return ResponseEntity.ok(blocks);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteUnavailability(@PathVariable Long id) {
        unavailabilityService.deleteUnavailability(id);
        return ResponseEntity.noContent().build();
    }
}