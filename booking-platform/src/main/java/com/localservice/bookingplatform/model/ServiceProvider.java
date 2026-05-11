package com.localservice.bookingplatform.model;

import com.localservice.bookingplatform.enums.ApprovalStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_providers")
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategory category;

    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(length = 50)
    private String city;

    @Column(length = 255)
    private String address;

    @Column(name = "hourly_rate")
    private Double hourlyRate;

    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "avg_rating")
    private Double avgRating = 0.0;

    @Column(name = "total_reviews")
    private Integer totalReviews = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Column(name = "rejection_reason", length = 255)
    private String rejectionReason;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    public Long getId() { return id; }
    public User getUser() { return user; }
    public ServiceCategory getCategory() { return category; }
    public String getBusinessName() { return businessName; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public Double getHourlyRate() { return hourlyRate; }
    public Integer getExperienceYears() { return experienceYears; }
    public String getDescription() { return description; }
    public Double getAvgRating() { return avgRating; }
    public Integer getTotalReviews() { return totalReviews; }
    public ApprovalStatus getApprovalStatus() { return approvalStatus; }
    public String getRejectionReason() { return rejectionReason; }
    public LocalDateTime getRegisteredAt() { return registeredAt; }
    public LocalDateTime getReviewedAt() { return reviewedAt; }

    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setCategory(ServiceCategory category) { this.category = category; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public void setCity(String city) { this.city = city; }
    public void setAddress(String address) { this.address = address; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public void setDescription(String description) { this.description = description; }
    public void setAvgRating(Double avgRating) { this.avgRating = avgRating; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }
    public void setApprovalStatus(ApprovalStatus approvalStatus) { this.approvalStatus = approvalStatus; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
    public void setRegisteredAt(LocalDateTime registeredAt) { this.registeredAt = registeredAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}