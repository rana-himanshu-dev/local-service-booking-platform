package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.AnalyticsResponse;
import com.localservice.bookingplatform.dto.ApprovalRequest;
import com.localservice.bookingplatform.dto.ServiceProviderResponse;
import com.localservice.bookingplatform.dto.SupportTicketResponse;
import com.localservice.bookingplatform.service.AdminService;
import com.localservice.bookingplatform.service.ProviderService;
import com.localservice.bookingplatform.service.SupportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final ProviderService providerService;
    private final SupportService supportService;

    public AdminController(AdminService adminService, ProviderService providerService, SupportService supportService) {
        this.adminService = adminService;
        this.providerService = providerService;
        this.supportService = supportService;
    }

    @GetMapping("/analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsResponse> getAnalytics() {
        AnalyticsResponse response = adminService.getAnalytics();
        return ResponseEntity.ok(response);
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
    @GetMapping("/support/tickets")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SupportTicketResponse>> getAllTickets() {
        List<SupportTicketResponse> tickets = supportService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/support/tickets/{id}/resolve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SupportTicketResponse> resolveTicket(
            @PathVariable Long id,
            @RequestParam String adminResponse) {
        SupportTicketResponse response = supportService.resolveTicket(id, adminResponse);
        return ResponseEntity.ok(response);
    }
}