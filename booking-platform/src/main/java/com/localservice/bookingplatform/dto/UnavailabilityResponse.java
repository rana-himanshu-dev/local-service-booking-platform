package com.localservice.bookingplatform.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UnavailabilityResponse {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LocalDateTime createdAt;

    public UnavailabilityResponse(Long id, LocalDate startDate, LocalDate endDate,
                                  String reason, LocalDateTime createdAt) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public String getReason() { return reason; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}