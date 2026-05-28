package com.localservice.bookingplatform.dto;

public class ServiceCategoryResponse {

    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    private Boolean isActive;

    public ServiceCategoryResponse(Long id, String name, String description, String iconUrl, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIconUrl() { return iconUrl; }
    public Boolean getIsActive() { return isActive; }
}