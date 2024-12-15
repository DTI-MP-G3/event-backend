package com.event.event.infrastructure.events.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.events.dto.EventOrganizer.UpdateEventDTO;
import com.event.event.infrastructure.security.Claims;
import com.event.event.usecase.event.EventOrganizerEventUsecase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/events/eo")
@PreAuthorize("hasAuthority('SCOPE_EVENT_ORGANIZER')")
public class EventOrganizerController {
    private EventOrganizerEventUsecase eventOrganizerEventUsecase;

    public EventOrganizerController(EventOrganizerEventUsecase eventOrganizerEventUsecase) {
        this.eventOrganizerEventUsecase = eventOrganizerEventUsecase;
    }

    @GetMapping
    public ResponseEntity<?> getAllEvents() {
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Events retrieved",
                eventOrganizerEventUsecase.getAllEvents(userId));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEvent(@PathVariable Integer eventId) {
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Event details retrieved",
                eventOrganizerEventUsecase.getEvent(eventId, userId));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable Integer eventId,
                                         @Valid @RequestBody UpdateEventDTO request) {
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Event updated",
                eventOrganizerEventUsecase.updateEvent(eventId, request, userId));
    }

    @PutMapping("publish/{eventId}")
    public ResponseEntity<?> publishEvent(@PathVariable @NotNull Integer eventId){
        Long userId = Claims.getUserIdFromJwt();
        eventOrganizerEventUsecase.publishEvent(eventId,userId);
        return ApiResponse.successfulResponse("Event published ");
    }
}
