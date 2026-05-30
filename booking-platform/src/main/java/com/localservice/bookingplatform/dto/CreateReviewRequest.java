package com.localservice.bookingplatform.dto;

import jakarta.validation.constraints.*;

public class CreateReviewRequest {
    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be 1-5")
    @Max(value = 5, message = "Rating must be 1-5")
    private Integer rating;

    @NotBlank(message = "Comment is required")
    @Size(min = 5, max = 500, message = "Comment must be 5-500 characters")
    private String comment;


    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}