package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreatePaymentRequest;
import com.localservice.bookingplatform.dto.PaymentResponse;
import com.localservice.bookingplatform.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentResponse> createPaymentOrder(
            @Valid @RequestBody CreatePaymentRequest request) {
        PaymentResponse response = paymentService.createPaymentOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/verify")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentResponse> verifyPayment(
            @RequestParam Long paymentId,
            @RequestParam String razorpayPaymentId,
            @RequestParam String razorpaySignature) {
        PaymentResponse response = paymentService.verifyPayment(
                paymentId, razorpayPaymentId, razorpaySignature);
        return ResponseEntity.ok(response);
    }
}