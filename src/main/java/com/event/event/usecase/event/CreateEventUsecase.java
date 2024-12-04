package com.event.event.usecase.event;

import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;

public interface CreateEventUsecase {
    Event createEvent(CreateEventRequestDTO req);


}
