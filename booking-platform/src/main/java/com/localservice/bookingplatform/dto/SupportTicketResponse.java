package com.localservice.bookingplatform.dto;

import java.time.LocalDateTime;

public class SupportTicketResponse {
    private Long id;
    private String userEmail;
    private String subject;
    private String description;
    private String category;
    private String status;  // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    private String adminResponse;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    public SupportTicketResponse(Long id, String userEmail, String subject,
                                 String description, String category, String status,
                                 String adminResponse, LocalDateTime createdAt,
                                 LocalDateTime resolvedAt) {
        this.id = id;
        this.userEmail = userEmail;
        this.subject = subject;
        this.description = description;
        this.category = category;
        this.status = status;
        this.adminResponse = adminResponse;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }

    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getSubject() { return subject; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getAdminResponse() { return adminResponse; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }
}