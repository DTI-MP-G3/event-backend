package com.event.event.infrastructure.events.dto.EventOrganizer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetEventsResponseDTO {
    private Long organizerId;
    private List<EventSummaryDTO> events;
}
