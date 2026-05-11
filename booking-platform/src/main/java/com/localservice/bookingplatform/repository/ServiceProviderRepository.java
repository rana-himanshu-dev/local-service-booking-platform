package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.enums.ApprovalStatus;
import com.localservice.bookingplatform.model.ServiceProvider;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {

    // Find provider by their user account
    Optional<ServiceProvider> findByUser(User user);

    // Find all providers by approval status — used by admin
    List<ServiceProvider> findByApprovalStatus(ApprovalStatus status);

    // Find approved providers by city — used in search
    List<ServiceProvider> findByApprovalStatusAndCity(
            ApprovalStatus status, String city);

    // Find approved providers by category — used in search
    List<ServiceProvider> findByApprovalStatusAndCategoryId(
            ApprovalStatus status, Long categoryId);

    // Search by city AND category together
    List<ServiceProvider> findByApprovalStatusAndCityAndCategoryId(
            ApprovalStatus status, String city, Long categoryId);

    // Custom query — search by keyword in business name or description
    @Query("SELECT sp FROM ServiceProvider sp WHERE sp.approvalStatus = 'APPROVED'" +
            " AND (LOWER(sp.businessName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            " OR LOWER(sp.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<ServiceProvider> searchByKeyword(@Param("keyword") String keyword);
}