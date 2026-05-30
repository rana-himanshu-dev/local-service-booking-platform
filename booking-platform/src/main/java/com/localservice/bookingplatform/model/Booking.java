package com.localservice.bookingplatform.model;

import com.localservice.bookingplatform.dto.BookingResponse;
import com.localservice.bookingplatform.enums.BookingStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;


    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private ServiceProvider provider;


    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false)
    private TimeSlot slot;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ── Getters ──────────────────────────────

    public Long getId() { return id; }
    public User getCustomer() { return customer; }
    public ServiceProvider getProvider() { return provider; }
    public TimeSlot getTimeSlot() { return slot; }
    public BookingStatus getStatus() { return status; }
    public Double getTotalAmount() { return totalAmount; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ── Setters ──────────────────────────────

    public void setId(Long id) { this.id = id; }
    public void setCustomer(User customer) { this.customer = customer; }
    public void setProvider(ServiceProvider provider) { this.provider = provider; }
    public void setSlot(TimeSlot slot) { this.slot = slot; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public void setTimeSlot(TimeSlot slot) {
        this.slot = slot;

    }

}