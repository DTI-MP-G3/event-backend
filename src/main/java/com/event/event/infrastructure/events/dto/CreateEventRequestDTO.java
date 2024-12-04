package com.event.event.infrastructure.events.dto;


import com.event.event.entity.Event;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEventRequestDTO {
    private String name;

    @NotNull(message = "Description is required")
    @Size(max = 4000, message = "Description must not exceed 4000 characters")
    private String description;
    @NotNull(message = "Venue is required")
    @Size(max = 255, message = "Venue must not exceed 255 characters")
    private String venue;

    @NotNull(message = "Event date is required")
    @FutureOrPresent (message = "Event date must be in present or future")
    private OffsetDateTime eventDate;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private Integer totalTickets;

    // Conversion method to Entity
    public Event toEntity() {
        Event event = new Event();
        event.setName(this.name);
        event.setVenue(this.venue);
        event.setDescription(this.description);
        event.setEventDate(this.eventDate);
        event.setStartTime(this.startTime);
        event.setEndTime(this.endTime);
        event.setTotalTickets(this.totalTickets);
        // Default values will be set by the entity
        return event;
    }


}
