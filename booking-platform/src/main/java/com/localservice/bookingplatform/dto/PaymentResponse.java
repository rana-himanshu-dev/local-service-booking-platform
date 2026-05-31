package com.localservice.bookingplatform.dto;

import java.time.LocalDateTime;

public class PaymentResponse {
    private Long id;
    private String razorpayOrderId;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;

    public PaymentResponse(Long id, String razorpayOrderId, Double amount,
                           String status, LocalDateTime createdAt) {
        this.id = id;
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getRazorpayOrderId() { return razorpayOrderId; }
    public Double getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}