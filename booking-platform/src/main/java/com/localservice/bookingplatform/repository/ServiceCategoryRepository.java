package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.ServiceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {

    // Find category by name — used when admin creates categories
    Optional<ServiceCategory> findByName(String name);

    // Find all active categories — shown to customers on homepage
    List<ServiceCategory> findByIsActive(Boolean isActive);

    // Check if category name already exists
    boolean existsByName(String name);
}