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


    List<Review> findByProvider(ServiceProvider provider);


    List<Review> findByCustomer(User customer);


    Optional<Review> findByBooking(Booking booking);


    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.provider = :provider")
    Double getAverageRatingForProvider(@Param("provider") ServiceProvider provider);

    List<Review> findByProviderId(Long providerId);

}