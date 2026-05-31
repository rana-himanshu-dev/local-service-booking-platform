package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreateSupportTicketRequest;
import com.localservice.bookingplatform.dto.SupportTicketResponse;
import com.localservice.bookingplatform.service.SupportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportController {

    private final SupportService supportService;

    public SupportController(SupportService supportService) {
        this.supportService = supportService;
    }


    @PostMapping("/tickets")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SupportTicketResponse> createTicket(
            @Valid @RequestBody CreateSupportTicketRequest request) {
        SupportTicketResponse response = supportService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/tickets")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<SupportTicketResponse>> getMyTickets() {
        List<SupportTicketResponse> tickets = supportService.getMyTickets();
        return ResponseEntity.ok(tickets);
    }
}

