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

    // Many TimeSlots → One ServiceProvider
    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider provider;

    @Column(name = "slot_date", nullable = false)
    private LocalDate slotDate;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "is_booked")
    private Boolean isBooked = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── Getters ──────────────────────────────

    public Long getId() { return id; }
    public ServiceProvider getProvider() { return provider; }
    public LocalDate getSlotDate() { return slotDate; }
    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }
    public Boolean getIsBooked() { return isBooked; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ── Setters ──────────────────────────────

    public void setId(Long id) { this.id = id; }
    public void setProvider(ServiceProvider provider) { this.provider = provider; }
    public void setSlotDate(LocalDate slotDate) { this.slotDate = slotDate; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public void setIsBooked(Boolean isBooked) { this.isBooked = isBooked; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}