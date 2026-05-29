package com.localservice.bookingplatform.dto;

public class UpdateBookingStatusRequest {
    private Long bookingId;
    private String status;
    private String reason;

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}