package com.localservice.bookingplatform.dto;

public class ServiceProviderResponse {
    private Long id;
    private String businessName;
    private String city;
    private String address;
    private Double hourlyRate;
    private Integer experienceYears;
    private String description;
    private String categoryName;
    private Double averageRating;
    private Integer totalReviews;
    private String approvalStatus;
    private String providerEmail;
    public ServiceProviderResponse(Long id, String businessName, String city,
                                   String address, Double hourlyRate,
                                   Integer experienceYears, String description,
                                   String categoryName, Double averageRating,
                                   Integer totalReviews, String approvalStatus,
                                   String providerEmail) {
        this.id = id;
        this.businessName = businessName;
        this.city = city;
        this.address = address;
        this.hourlyRate = hourlyRate;
        this.experienceYears = experienceYears;
        this.description = description;
        this.categoryName = categoryName;
        this.averageRating = averageRating;
        this.totalReviews = totalReviews;
        this.approvalStatus = approvalStatus;
        this.providerEmail = providerEmail;
    }


    public Long getId() { return id; }
    public String getBusinessName() { return businessName; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public Double getHourlyRate() { return hourlyRate; }
    public Integer getExperienceYears() { return experienceYears; }
    public String getDescription() { return description; }
    public String getCategoryName() { return categoryName; }
    public Double getAverageRating() { return averageRating; }
    public Integer getTotalReviews() { return totalReviews; }
    public String getApprovalStatus() { return approvalStatus; }
    public String getProviderEmail() { return providerEmail; }
}