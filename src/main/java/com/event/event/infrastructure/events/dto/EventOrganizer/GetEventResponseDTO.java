package com.event.event.infrastructure.events.dto.EventOrganizer;

import com.event.event.enums.EventStatus;
import com.event.event.infrastructure.ticketTypes.dto.TicketTypeDTO;
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
public class GetEventResponseDTO {
    private Integer id;
    private Long organizerId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String venue;

    @NotNull
    @Size(max = 4000)
    private String description;

    private OffsetDateTime eventDate;
    private OffsetDateTime startTime;
    private OffsetDateTime endTime;
    private Integer totalTickets;
    private EventStatus status;
    private Set<TicketTypeDTO> ticketTypes;
}
