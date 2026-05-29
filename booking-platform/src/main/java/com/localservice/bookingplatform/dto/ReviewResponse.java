package com.localservice.bookingplatform.dto;

import java.time.LocalDateTime;

public class ReviewResponse {
    private Long id;
    private String customerName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public ReviewResponse(Long id, String customerName, Integer rating,
                          String comment, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public Integer getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}