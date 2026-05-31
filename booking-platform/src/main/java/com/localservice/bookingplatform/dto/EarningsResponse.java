package com.localservice.bookingplatform.dto;

public class EarningsResponse {
    private Double totalEarnings;
    private Double commission;
    private Double netEarnings;
    private Integer totalBookings;

    public EarningsResponse(Double totalEarnings, Double commission,
                            Double netEarnings, Integer totalBookings) {
        this.totalEarnings = totalEarnings;
        this.commission = commission;
        this.netEarnings = netEarnings;
        this.totalBookings = totalBookings;
    }

    public Double getTotalEarnings() { return totalEarnings; }
    public Double getCommission() { return commission; }
    public Double getNetEarnings() { return netEarnings; }
    public Integer getTotalBookings() { return totalBookings; }
}