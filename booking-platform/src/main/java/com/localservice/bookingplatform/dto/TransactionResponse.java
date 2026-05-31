package com.localservice.bookingplatform.dto;

import java.time.LocalDateTime;

public class TransactionResponse {
    private Long bookingId;
    private String customerName;
    private Double amount;
    private Double commission;
    private Double netAmount;
    private String status;
    private LocalDateTime date;

    public TransactionResponse(Long bookingId, String customerName, Double amount,
                               Double commission, Double netAmount, String status,
                               LocalDateTime date) {
        this.bookingId = bookingId;
        this.customerName = customerName;
        this.amount = amount;
        this.commission = commission;
        this.netAmount = netAmount;
        this.status = status;
        this.date = date;
    }

    public Long getBookingId() { return bookingId; }
    public String getCustomerName() { return customerName; }
    public Double getAmount() { return amount; }
    public Double getCommission() { return commission; }
    public Double getNetAmount() { return netAmount; }
    public String getStatus() { return status; }
    public LocalDateTime getDate() { return date; }
}