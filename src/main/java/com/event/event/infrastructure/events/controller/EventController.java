package com.event.event.infrastructure.events.controller;

import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;
import com.event.event.usecase.event.CreateEventUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/events")
public class EventController {
    private final CreateEventUsecase createEventUsecase;

    public EventController(CreateEventUsecase createEventUsecase) {
        this.createEventUsecase = createEventUsecase;
    }


    @PreAuthorize("hasAuthority('SCOPE_EVENT_ORGANIZER')")
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDTO req){
        return ApiResponse.successfulResponse("Create New User Success",createEventUsecase.createEvent(req));
    }




}
