package com.localservice.bookingplatform.dto;

import jakarta.validation.constraints.*;

public class CreateServiceProviderRequest {
    @NotBlank(message = "Business name is required")
    @Size(min = 2, max = 100, message = "Business name must be 2-100 characters")
    private String businessName;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Hourly rate is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Rate must be greater than 0")
    private Double hourlyRate;

    @NotNull(message = "Experience years is required")
    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 60, message = "Experience cannot exceed 60 years")
    private Integer experienceYears;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be 10-500 characters")
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
}