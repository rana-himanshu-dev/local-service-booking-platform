package com.localservice.bookingplatform.dto;

import java.time.LocalDateTime;

public class BookingResponse {
    private Long id;
    private String customerEmail;
    private String providerName;
    private String slotDate;
    private String startTime;
    private String status;
    private Double totalAmount;
    private String notes;
    private LocalDateTime createdAt;
    private Boolean isBooked;

    public BookingResponse(Long id, String customerEmail, String providerName,
                           String slotDate, String startTime, String status,
                           Double totalAmount, String notes, LocalDateTime createdAt) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.providerName = providerName;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.status = status;
        this.totalAmount = totalAmount;
        this.notes = notes;
        this.createdAt = createdAt;
        this.isBooked = isBooked;
    }

    public Long getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public String getProviderName() { return providerName; }
    public String getSlotDate() { return slotDate; }
    public String getStartTime() { return startTime; }
    public String getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }


}