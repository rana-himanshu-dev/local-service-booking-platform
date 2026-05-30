package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreateServiceProviderRequest;
import com.localservice.bookingplatform.dto.ServiceProviderResponse;
import com.localservice.bookingplatform.service.ProviderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/profile")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'PROVIDER', 'ADMIN')")
    public ResponseEntity<ServiceProviderResponse> createProfile(
           @Valid @RequestBody CreateServiceProviderRequest request) {
        ServiceProviderResponse response = providerService.createProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ServiceProviderResponse> updateProfile(
            @RequestBody CreateServiceProviderRequest request) {
        ServiceProviderResponse response = providerService.updateProfile(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ServiceProviderResponse> getProfile(
            @PathVariable Long id) {
        ServiceProviderResponse response = providerService.getProfileById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/search")
    public ResponseEntity<List<ServiceProviderResponse>> searchProviders(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword) {

        List<ServiceProviderResponse> results =
                providerService.searchProviders(city, categoryId, keyword);
        return ResponseEntity.ok(results);
    }
}
