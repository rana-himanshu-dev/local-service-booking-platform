package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.enums.BookingStatus;
import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomer(User customer);
    List<Booking> findByProvider(ServiceProvider provider);
    List<Booking> findByCustomerAndStatus(User customer, BookingStatus status);
    List<Booking> findByProviderAndStatus(
            ServiceProvider provider, BookingStatus status);
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    Long countByStatus(@Param("status") BookingStatus status);
    @Query("SELECT SUM(b.totalAmount) FROM Booking b WHERE b.status = 'COMPLETED'")
    Double getTotalRevenue();
    @Query("SELECT SUM(b.totalAmount) FROM Booking b " +
            "WHERE b.provider = :provider AND b.status = 'COMPLETED'")
    Double getProviderEarnings(@Param("provider") ServiceProvider provider);
}