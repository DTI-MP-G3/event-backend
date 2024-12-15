package com.event.event.infrastructure.tickets.dto.User;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class UserEventTicketsDTO {
    private Integer eventId;
    private String eventName;
    private String eventLocation;
    private OffsetDateTime eventDate;
    private String eventStatus;
    private Map<String, List<TicketDetailDTO>> ticketsByType;
}
