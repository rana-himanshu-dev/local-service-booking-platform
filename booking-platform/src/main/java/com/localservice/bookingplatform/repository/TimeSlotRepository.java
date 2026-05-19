package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {


    List<TimeSlot> findByProvider(ServiceProvider provider);


    List<TimeSlot> findByProviderAndSlotDateAndIsBooked(
            ServiceProvider provider, LocalDate slotDate, Boolean isBooked);


    List<TimeSlot> findByProviderAndIsBooked(
            ServiceProvider provider, Boolean isBooked);


    List<TimeSlot> findByProviderAndSlotDate(
            ServiceProvider provider, LocalDate slotDate);
}