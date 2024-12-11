package com.event.event.usecase.event;

import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;
import com.event.event.infrastructure.events.dto.CreateEventResponseDTO;

public interface CreateEventUsecase {
    CreateEventResponseDTO createEvent(CreateEventRequestDTO req, Long userId);


}
