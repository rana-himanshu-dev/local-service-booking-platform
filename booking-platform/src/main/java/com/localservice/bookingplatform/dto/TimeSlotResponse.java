package com.localservice.bookingplatform.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeSlotResponse {
    private Long id;
    private LocalDate slotDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isBooked;

    public TimeSlotResponse(Long id, LocalDate slotDate,
                            LocalTime startTime, LocalTime endTime,
                            Boolean isBooked) {
        this.id = id;
        this.slotDate = slotDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isBooked = isBooked;
    }

    public Long getId() { return id; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Boolean getIsBooked() { return isBooked; }
}