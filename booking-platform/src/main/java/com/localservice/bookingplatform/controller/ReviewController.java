package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreateReviewRequest;
import com.localservice.bookingplatform.dto.ReviewResponse;
import com.localservice.bookingplatform.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ReviewResponse> createReview(
           @Valid @RequestBody CreateReviewRequest request) {
        ReviewResponse response = reviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<ReviewResponse>> getProviderReviews(
            @PathVariable Long providerId) {
        List<ReviewResponse> reviews = reviewService.getProviderReviews(providerId);
        return ResponseEntity.ok(reviews);
    }
}