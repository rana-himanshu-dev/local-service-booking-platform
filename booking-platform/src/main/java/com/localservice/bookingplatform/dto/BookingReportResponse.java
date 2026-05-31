package com.localservice.bookingplatform.dto;

public class BookingReportResponse {
    private Long totalBookings;
    private Long completedBookings;
    private Long pendingBookings;
    private Long cancelledBookings;
    private Double totalRevenue;
    private Double averageBookingValue;

    public BookingReportResponse(Long totalBookings, Long completedBookings,
                                 Long pendingBookings, Long cancelledBookings,
                                 Double totalRevenue, Double averageBookingValue) {
        this.totalBookings = totalBookings;
        this.completedBookings = completedBookings;
        this.pendingBookings = pendingBookings;
        this.cancelledBookings = cancelledBookings;
        this.totalRevenue = totalRevenue;
        this.averageBookingValue = averageBookingValue;
    }

    public Long getTotalBookings() { return totalBookings; }
    public Long getCompletedBookings() { return completedBookings; }
    public Long getPendingBookings() { return pendingBookings; }
    public Long getCancelledBookings() { return cancelledBookings; }
    public Double getTotalRevenue() { return totalRevenue; }
    public Double getAverageBookingValue() { return averageBookingValue; }
}