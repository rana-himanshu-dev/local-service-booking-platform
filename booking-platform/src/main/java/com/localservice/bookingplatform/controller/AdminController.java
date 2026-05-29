package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.ApprovalRequest;
import com.localservice.bookingplatform.dto.ServiceProviderResponse;
import com.localservice.bookingplatform.service.ProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ProviderService providerService;

    public AdminController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/providers/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServiceProviderResponse>> getPendingProviders() {
        List<ServiceProviderResponse> providers = providerService.getPendingProviders();
        return ResponseEntity.ok(providers);
    }


    @PostMapping("/providers/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceProviderResponse> approveProvider(
            @RequestBody ApprovalRequest request) {
        ServiceProviderResponse response = providerService.approveProvider(
                request.getProviderId(),
                request.getApproved(),
                request.getRemarks()
        );
        return ResponseEntity.ok(response);
    }
}