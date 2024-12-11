package com.event.event.infrastructure.events.dto;


import com.event.event.entity.Event;
import com.event.event.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventResponseDTO {
    private Integer id;
    private String name;
    private String venue;
    private OffsetDateTime eventDate;
    private EventStatus status;

    public CreateEventResponseDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.venue = event.getVenue();
        this.eventDate = event.getEventDate();
        this.status = event.getStatus();
    }
}
