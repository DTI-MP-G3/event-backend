package com.event.event.usecase.event;

import com.event.event.infrastructure.events.dto.EventOrganizer.GetEventResponseDTO;
import com.event.event.infrastructure.events.dto.EventOrganizer.GetEventsResponseDTO;
import com.event.event.infrastructure.events.dto.EventOrganizer.UpdateEventDTO;

public interface EventOrganizerEventUsecase {


    GetEventsResponseDTO getAllEvents(Long organizerId);
    GetEventResponseDTO getEvent(Integer eventId, Long organizerId);
    GetEventResponseDTO updateEvent(Integer eventId, UpdateEventDTO request, Long organizerId);
    void publishEvent(Integer eventId, Long userId);

}
