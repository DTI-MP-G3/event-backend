package com.event.event.infrastructure.events.controller;

import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;
import com.event.event.usecase.event.CreateEventUsecase;
import com.event.event.usecase.event.SearchEventUseCase;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequestMapping("api/v1/events")
public class EventController {
    private final CreateEventUsecase createEventUsecase;
    private final SearchEventUseCase searchEventUseCase;



    public EventController(CreateEventUsecase createEventUsecase, com.event.event.usecase.event.SearchEventUseCase searchEventUseCase) {
        this.createEventUsecase = createEventUsecase;
        this.searchEventUseCase = searchEventUseCase;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchEvents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String venue,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime eventDate
    ) {
        return ApiResponse.successfulResponse("Create New User Success",searchEventUseCase.searchEvents(name, venue, eventDate));
    }

    @GetMapping("/search/paged")
    public ResponseEntity<?> searchEventsPaged(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String venue,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime eventDate,
            Pageable pageable
    ) {
        return ApiResponse.successfulResponse("Create New User Success",searchEventUseCase.searchEvents(name, venue, eventDate,pageable));
    }


    @PreAuthorize("hasAuthority('SCOPE_EVENT_ORGANIZER')")
    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody CreateEventRequestDTO req){
        return ApiResponse.successfulResponse("Create New User Success",createEventUsecase.createEvent(req));
    }




}
