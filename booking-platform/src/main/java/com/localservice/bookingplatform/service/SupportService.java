package com.localservice.bookingplatform.service;

import com.localservice.bookingplatform.dto.CreateSupportTicketRequest;
import com.localservice.bookingplatform.dto.SupportTicketResponse;
import com.localservice.bookingplatform.model.SupportTicket;
import com.localservice.bookingplatform.model.User;
import com.localservice.bookingplatform.repository.SupportTicketRepository;
import com.localservice.bookingplatform.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupportService {

    private final SupportTicketRepository ticketRepository;
    private final UserRepository userRepository;

    public SupportService(SupportTicketRepository ticketRepository,
                          UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }


    public SupportTicketResponse createTicket(CreateSupportTicketRequest request) {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        SupportTicket ticket = new SupportTicket();
        ticket.setUser(user);
        ticket.setSubject(request.getSubject());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(LocalDateTime.now());

        SupportTicket saved = ticketRepository.save(ticket);
        return convertToResponse(saved);
    }


    public List<SupportTicketResponse> getMyTickets() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByUser(user)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public List<SupportTicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public SupportTicketResponse resolveTicket(Long ticketId, String adminResponse) {
        SupportTicket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus("RESOLVED");
        ticket.setAdminResponse(adminResponse);
        ticket.setResolvedAt(LocalDateTime.now());

        SupportTicket updated = ticketRepository.save(ticket);
        return convertToResponse(updated);
    }

    private SupportTicketResponse convertToResponse(SupportTicket ticket) {
        return new SupportTicketResponse(
                ticket.getId(),
                ticket.getUser().getEmail(),
                ticket.getSubject(),
                ticket.getDescription(),
                ticket.getCategory(),
                ticket.getStatus(),
                ticket.getAdminResponse(),
                ticket.getCreatedAt(),
                ticket.getResolvedAt()
        );
    }
}