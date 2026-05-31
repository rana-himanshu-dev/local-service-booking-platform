package com.localservice.bookingplatform.dto;

import jakarta.validation.constraints.*;

public class CreateNotificationRequest {
    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Message required")
    @Size(min = 5, max = 500, message = "Message must be 5-500 characters")
    private String message;

    @NotBlank(message = "Type required")
    private String type;  // BOOKING, PAYMENT, MESSAGE, ADMIN

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}