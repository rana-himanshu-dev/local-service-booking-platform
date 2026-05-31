package com.localservice.bookingplatform.dto;

public class AnalyticsResponse {
    private Long totalUsers;
    private Long totalProviders;
    private Long totalBookings;
    private Long completedBookings;
    private Double totalRevenue;
    private Double platformCommission;
    private Double averageRating;

    public AnalyticsResponse(Long totalUsers, Long totalProviders, Long totalBookings,
                             Long completedBookings, Double totalRevenue,
                             Double platformCommission, Double averageRating) {
        this.totalUsers = totalUsers;
        this.totalProviders = totalProviders;
        this.totalBookings = totalBookings;
        this.completedBookings = completedBookings;
        this.totalRevenue = totalRevenue;
        this.platformCommission = platformCommission;
        this.averageRating = averageRating;
    }

    public Long getTotalUsers() { return totalUsers; }
    public Long getTotalProviders() { return totalProviders; }
    public Long getTotalBookings() { return totalBookings; }
    public Long getCompletedBookings() { return completedBookings; }
    public Double getTotalRevenue() { return totalRevenue; }
    public Double getPlatformCommission() { return platformCommission; }
    public Double getAverageRating() { return averageRating; }
}