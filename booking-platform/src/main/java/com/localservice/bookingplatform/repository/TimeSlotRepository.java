package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    // All slots for a provider — used on provider dashboard
    List<TimeSlot> findByProvider(ServiceProvider provider);

    // Available slots for a provider on a specific date — shown to customer
    List<TimeSlot> findByProviderAndSlotDateAndIsBooked(
            ServiceProvider provider, LocalDate slotDate, Boolean isBooked);

    // All available slots for a provider — used in booking flow
    List<TimeSlot> findByProviderAndIsBooked(
            ServiceProvider provider, Boolean isBooked);

    // All slots on a specific date — used for conflict checking
    List<TimeSlot> findByProviderAndSlotDate(
            ServiceProvider provider, LocalDate slotDate);
}