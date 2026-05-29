package com.localservice.bookingplatform.controller;

import com.localservice.bookingplatform.dto.CreateTimeSlotRequest;
import com.localservice.bookingplatform.dto.TimeSlotResponse;
import com.localservice.bookingplatform.service.TimeSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    // POST /api/slots - Create slot (PROVIDER only)
    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<TimeSlotResponse> createSlot(
            @RequestBody CreateTimeSlotRequest request) {
        TimeSlotResponse response = timeSlotService.createTimeSlot(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /api/slots/provider/{providerId}/available - View available slots
    @GetMapping("/provider/{providerId}/available")
    public ResponseEntity<List<TimeSlotResponse>> getAvailableSlots(
            @PathVariable Long providerId) {
        List<TimeSlotResponse> slots = timeSlotService.getAvailableSlots(providerId);
        return ResponseEntity.ok(slots);
    }

    // GET /api/slots/provider/{providerId} - View all slots
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<TimeSlotResponse>> getProviderSlots(
            @PathVariable Long providerId) {
        List<TimeSlotResponse> slots = timeSlotService.getProviderSlots(providerId);
        return ResponseEntity.ok(slots);
    }

    // DELETE /api/slots/{slotId} - Delete slot (PROVIDER only)
    @DeleteMapping("/{slotId}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long slotId) {
        timeSlotService.deleteSlot(slotId);
        return ResponseEntity.noContent().build();
    }
}