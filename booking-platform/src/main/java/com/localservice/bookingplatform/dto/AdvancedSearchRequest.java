package com.localservice.bookingplatform.dto;

public class AdvancedSearchRequest {
    private String city;
    private Long categoryId;
    private Double minRating;  // e.g., 4.0
    private Double maxHourlyRate;  // e.g., 1000.0
    private Integer minExperience;  // e.g., 2 years
    private String keyword;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }

    public Double getMaxHourlyRate() { return maxHourlyRate; }
    public void setMaxHourlyRate(Double maxHourlyRate) { this.maxHourlyRate = maxHourlyRate; }

    public Integer getMinExperience() { return minExperience; }
    public void setMinExperience(Integer minExperience) { this.minExperience = minExperience; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
}