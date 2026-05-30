package com.localservice.bookingplatform.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "time_slots")
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ServiceProvider provider;

    @Column
    private LocalDate slotDate;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    @Column
    private Boolean isBooked;


    public Long getId() { return id; }
    public ServiceProvider getProvider() { return provider; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Boolean getIsBooked() { return isBooked; }


    public void setProvider(ServiceProvider provider) { this.provider = provider; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setIsBooked(Boolean isBooked) { this.isBooked = isBooked; }
}