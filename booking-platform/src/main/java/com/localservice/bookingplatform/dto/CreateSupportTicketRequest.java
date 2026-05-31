package com.localservice.bookingplatform.dto;

import jakarta.validation.constraints.*;

public class CreateSupportTicketRequest {
    @NotBlank(message = "Subject required")
    @Size(min = 5, max = 100, message = "Subject must be 5-100 characters")
    private String subject;

    @NotBlank(message = "Description required")
    @Size(min = 10, max = 1000, message = "Description must be 10-1000 characters")
    private String description;

    @NotBlank(message = "Category required")
    private String category;  // BUG, FEATURE_REQUEST, COMPLAINT, OTHER

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}