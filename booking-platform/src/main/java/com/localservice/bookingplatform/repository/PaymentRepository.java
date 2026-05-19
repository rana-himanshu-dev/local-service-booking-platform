package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {


    Optional<Payment> findByBooking(Booking booking);


    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);


    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);
}