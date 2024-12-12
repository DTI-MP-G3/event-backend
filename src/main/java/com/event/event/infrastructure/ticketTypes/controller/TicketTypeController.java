package com.event.event.infrastructure.ticketTypes.controller;

import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.security.Claims;
import com.event.event.infrastructure.ticketTypes.dto.CreateTicketTypeRequestDTO;
import com.event.event.infrastructure.ticketTypes.dto.TicketTypeDTO;
import com.event.event.usecase.ticketType.CreateTicketTypeUsecase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket-types")
public class TicketTypeController {

    private final CreateTicketTypeUsecase createTicketTypeUsecase;

    public TicketTypeController(CreateTicketTypeUsecase createTicketTypeUsecase) {
        this.createTicketTypeUsecase = createTicketTypeUsecase;
    }

    @PreAuthorize("hasAuthority('SCOPE_EVENT_ORGANIZER')")
    @PostMapping
    public ResponseEntity<?> createBulkTicketTypes(@Valid @RequestBody CreateTicketTypeRequestDTO req){
        Long userId = Claims.getUserIdFromJwt();
        createTicketTypeUsecase.createBulkTicketTypes(req.getEventId(), req.getTicketTypes(), userId);
        return ApiResponse.successfulResponse("Ticket types created successfully");
    }


    @PreAuthorize("hasAuthority('SCOPE_EVENT_ORGANIZER')")
    @PostMapping("/{eventId}")
    public ResponseEntity<?> createBulkTicketTypes(@Valid @RequestBody TicketTypeDTO req, @PathVariable("eventId") Integer eventId){
        Long userId = Claims.getUserIdFromJwt();
        createTicketTypeUsecase.createTicketTypes(eventId, req, userId);
        return ApiResponse.successfulResponse("Ticket type created successfully");
    }

}
