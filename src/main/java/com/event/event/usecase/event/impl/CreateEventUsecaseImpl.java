package com.event.event.usecase.event.impl;

import com.event.event.entity.Event;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.usecase.event.CreateEventUsecase;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Log
@Service
public class CreateEventUsecaseImpl implements CreateEventUsecase {

    private  final EventRepository eventRepository ;

    public CreateEventUsecaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(CreateEventRequestDTO req) {
        Event newEvent = req.toEntity();
        try{
            eventRepository.save(newEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        };
        return newEvent;
    }
}
