package com.localservice.bookingplatform.dto;

public class CreateBookingRequest {
    private Long timeSlotId;
    private String notes;

    public Long getTimeSlotId() { return timeSlotId; }
    public void setTimeSlotId(Long timeSlotId) { this.timeSlotId = timeSlotId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}