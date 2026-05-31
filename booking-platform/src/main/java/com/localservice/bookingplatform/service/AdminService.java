package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.AnalyticsResponse;
import com.localservice.bookingplatform.enums.BookingStatus;
import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.Review;
import com.localservice.bookingplatform.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ServiceProviderRepository providerRepository;
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public AdminService(UserRepository userRepository,
                        ServiceProviderRepository providerRepository,
                        BookingRepository bookingRepository,
                        ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.providerRepository = providerRepository;
        this.bookingRepository = bookingRepository;
        this.reviewRepository = reviewRepository;
    }


    public AnalyticsResponse getAnalytics() {

        Long totalUsers = userRepository.count();


        Long totalProviders = providerRepository.count();


        Long totalBookings = bookingRepository.count();


        List<Booking> allBookings = bookingRepository.findAll();
        Long completedBookings = allBookings.stream()
                .filter(b -> b.getStatus() == BookingStatus.COMPLETED)
                .count();


        Double totalRevenue = allBookings.stream()
                .mapToDouble(Booking::getTotalAmount)
                .sum();
        Double platformCommission = totalRevenue * 0.10;


        List<Review> allReviews = reviewRepository.findAll();
        Double averageRating = allReviews.isEmpty() ? 0.0 :
                allReviews.stream()
                        .mapToDouble(Review::getRating)
                        .average()
                        .orElse(0.0);

        return new AnalyticsResponse(
                totalUsers,
                totalProviders,
                totalBookings,
                completedBookings,
                totalRevenue,
                platformCommission,
                Math.round(averageRating * 10.0) / 10.0
        );
    }
}