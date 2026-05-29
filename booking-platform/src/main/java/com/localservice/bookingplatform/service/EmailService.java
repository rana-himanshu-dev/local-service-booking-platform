package com.localservice.bookingplatform.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendBookingConfirmation(String customerEmail, String providerName,
                                        String slotDate, String startTime,
                                        Double amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customerEmail);
        message.setSubject("Booking Confirmed - " + providerName);
        message.setText("Your booking with " + providerName + " is confirmed.\n\n" +
                "Date: " + slotDate + "\n" +
                "Time: " + startTime + "\n" +
                "Amount: ₹" + amount + "\n\n" +
                "Thank you for using our service!");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }

    public void sendProviderNotification(String providerEmail, String customerName,
                                         String slotDate, String startTime) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(providerEmail);
        message.setSubject("New Booking - " + customerName);
        message.setText("You have a new booking!\n\n" +
                "Customer: " + customerName + "\n" +
                "Date: " + slotDate + "\n" +
                "Time: " + startTime + "\n\n" +
                "Please confirm this booking in your dashboard.");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }

    public void sendCancellationEmail(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }
    public void sendApprovalEmail(String providerEmail, String businessName,
                                  Boolean approved, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(providerEmail);
        mailMessage.setSubject(approved ? "Profile Approved" : "Profile Rejected");
        mailMessage.setText("Dear " + businessName + ",\n\n" +
                message + "\n\n" +
                (approved ? "You can now start receiving bookings!" :
                        "Please update your profile and resubmit.") +
                "\n\nThank you!");

        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Email send failed: " + e.getMessage());
        }
    }
}