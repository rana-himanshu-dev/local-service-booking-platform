package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.Unavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnavailabilityRepository extends JpaRepository<Unavailability, Long> {
    List<Unavailability> findByProviderId(Long providerId);
}