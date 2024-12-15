package com.event.event.infrastructure.events.util;

import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.EventSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventSearchResponseMapper {
    public EventSearchResponseDTO toDTO(Event event) {
        return new EventSearchResponseDTO(
                event.getName(),
                event.getId(),
                event.getVenue(),
                event.getEventDate()

        );
    }

    public List<EventSearchResponseDTO> toDTOList(List<Event> events) {
        return events.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Page<EventSearchResponseDTO> toDTOPage(Page<Event> eventPage) {
        return eventPage.map(this::toDTO);
    }
}
