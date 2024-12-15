package com.event.event.infrastructure.events.dto.EventOrganizer;

import com.event.event.enums.EventStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventDTO {
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String venue;

    @NotNull
    @Size(max = 4000)
    private String description;

    @NotNull
    @FutureOrPresent
    private OffsetDateTime eventDate;

    @FutureOrPresent
    private OffsetDateTime startTime;

    @FutureOrPresent
    private OffsetDateTime endTime;

    private Integer totalTickets;
    private EventStatus status;
    private Set<UpdateTicketTypeDTO> ticketTypes;

    @Data
    public static class UpdateTicketTypeDTO {
        private Integer id;
        private String name;
        private Double price;
        private Integer quantity;
        private String description;
    }
}
