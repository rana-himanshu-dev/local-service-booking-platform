package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreatePaymentRequest;
import com.localservice.bookingplatform.dto.PaymentResponse;
import com.localservice.bookingplatform.enums.PaymentStatus;
import com.localservice.bookingplatform.model.Booking;
import com.localservice.bookingplatform.model.Payment;
import com.localservice.bookingplatform.repository.BookingRepository;
import com.localservice.bookingplatform.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import com.razorpay.Order;
import org.json.JSONObject;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final RazorpayClient razorpayClient;

    public PaymentService(PaymentRepository paymentRepository,
                          BookingRepository bookingRepository,
                          RazorpayClient razorpayClient) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.razorpayClient = razorpayClient;
    }


    public PaymentResponse createPaymentOrder(CreatePaymentRequest request) {
        try {
            Booking booking = bookingRepository.findById(request.getBookingId())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            // For now, generate a mock Razorpay order ID
            // (Full Razorpay integration will be tested separately)
            String mockOrderId = "order_" + System.currentTimeMillis();

            // Save payment record
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(request.getAmount());
            payment.setRazorpayOrderId(mockOrderId);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setCreatedAt(LocalDateTime.now());

            Payment saved = paymentRepository.save(payment);
            System.out.println("Payment created with Order ID: " + mockOrderId);

            return convertToResponse(saved);

        } catch (Exception e) {
            System.out.println("Payment Error: " + e.getMessage());
            throw new RuntimeException("Payment creation failed: " + e.getMessage());
        }
    }
    public PaymentResponse verifyPayment(Long paymentId, String razorpayPaymentId,
                                         String razorpaySignature) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));


        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setPaidAt(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment updated = paymentRepository.save(payment);
        return convertToResponse(updated);
    }

    private PaymentResponse convertToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getRazorpayOrderId(),
                payment.getAmount(),
                payment.getStatus().name(),
                payment.getCreatedAt()
        );
    }
}