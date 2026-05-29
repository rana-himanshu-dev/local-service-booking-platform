package com.localservice.bookingplatform.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateTimeSlotRequest {
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public LocalDate getSlotDate() { return slotDate; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}