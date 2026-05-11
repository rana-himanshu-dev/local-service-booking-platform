package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.Review;
import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // All reviews for a provider — shown on provider profile
    List<Review> findByProvider(ServiceProvider provider);

    // All reviews written by a customer
    List<Review> findByCustomer(User customer);

    // Check if review already exists for a booking
    Optional<Review> findByBooking(Booking booking);

    // Average rating for a provider — used to update provider's avg_rating
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.provider = :provider")
    Double getAverageRatingForProvider(@Param("provider") ServiceProvider provider);
}