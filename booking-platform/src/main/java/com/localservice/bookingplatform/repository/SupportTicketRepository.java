package com.localservice.bookingplatform.repository;

import com.localservice.bookingplatform.model.SupportTicket;
import com.localservice.bookingplatform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    List<SupportTicket> findByUser(User user);
    List<SupportTicket> findByStatus(String status);
}