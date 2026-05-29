package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateReviewRequest;
import com.localservice.bookingplatform.dto.ReviewResponse;
import com.localservice.bookingplatform.model.*;
import com.localservice.bookingplatform.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final ServiceProviderRepository providerRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         ServiceProviderRepository providerRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.providerRepository = providerRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    public ReviewResponse createReview(CreateReviewRequest request) {
        String email = getCurrentUserEmail();
        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));


        if (reviewRepository.findByBooking(booking).isPresent()) {
            throw new RuntimeException("This booking already has a review");
        }


        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setCustomer(customer);
        review.setProvider(booking.getProvider());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        Review saved = reviewRepository.save(review);


        updateProviderRating(booking.getProvider().getId());

        return convertToResponse(saved);
    }


    public List<ReviewResponse> getProviderReviews(Long providerId) {
        return reviewRepository.findByProviderId(providerId)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    private void updateProviderRating(Long providerId) {
        ServiceProvider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        List<Review> reviews = reviewRepository.findByProviderId(providerId);

        if (reviews.isEmpty()) {
            provider.setAvgRating(0.0);
            provider.setTotalReviews(0);
        } else {
            Double avgRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);

            provider.setAvgRating(Math.round(avgRating * 10.0) / 10.0);  // Round to 1 decimal
            provider.setTotalReviews(reviews.size());
        }

        providerRepository.save(provider);
    }

    private ReviewResponse convertToResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getCustomer().getFullName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}