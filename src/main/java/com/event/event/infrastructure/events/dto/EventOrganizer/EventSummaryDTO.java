package com.event.event.infrastructure.events.dto.EventOrganizer;

import com.event.event.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventSummaryDTO {

    private Integer id;
    private String name;
    private String venue;
    private OffsetDateTime eventDate;
    private EventStatus status;
    private Integer totalTickets;
    private Integer ticketsSold;
}
