package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.EarningsResponse;
import com.localservice.bookingplatform.dto.TransactionResponse;
import com.localservice.bookingplatform.service.ProviderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class EarningsController {

    private final ProviderService providerService;

    public EarningsController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/earnings")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<EarningsResponse> getEarnings() {
        EarningsResponse response = providerService.getEarnings();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<TransactionResponse>> getTransactions() {
        List<TransactionResponse> transactions = providerService.getTransactions();
        return ResponseEntity.ok(transactions);
    }
}