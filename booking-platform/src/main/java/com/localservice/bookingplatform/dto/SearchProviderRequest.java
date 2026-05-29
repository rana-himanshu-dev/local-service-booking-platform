package com.localservice.bookingplatform.dto;

public class SearchProviderRequest {
    private String city;
    private Long categoryId;
    private String keyword;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
}