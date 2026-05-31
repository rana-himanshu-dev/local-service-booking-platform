package com.localservice.bookingplatform.dto;

import jakarta.validation.constraints.*;

public class CreatePaymentRequest {
    @NotNull(message = "Booking ID required")
    private Long bookingId;

    @NotNull(message = "Amount required")
    @DecimalMin(value = "0.01", message = "Amount must be > 0")
    private Double amount;

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}