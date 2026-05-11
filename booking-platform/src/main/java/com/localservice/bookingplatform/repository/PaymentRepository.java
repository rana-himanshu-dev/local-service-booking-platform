package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find payment by booking — used after booking is confirmed
    Optional<Payment> findByBooking(Booking booking);

    // Find by Razorpay order ID — used in payment verification
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);

    // Find by Razorpay payment ID — used in webhook handling
    Optional<Payment> findByRazorpayPaymentId(String razorpayPaymentId);
}