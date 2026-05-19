package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.enums.Role;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


    boolean existsByEmail(String email);


    List<User> findByRole(Role role);

    // Find all active users
    List<User> findByIsActive(Boolean isActive);
}