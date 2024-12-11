package com.event.event.usecase.event.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.Event;
import com.event.event.entity.User;
import com.event.event.infrastructure.events.dto.CreateEventRequestDTO;
import com.event.event.infrastructure.events.dto.CreateEventResponseDTO;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.event.CreateEventUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log
@Service
public class CreateEventUsecaseImpl implements CreateEventUsecase {

    private  final EventRepository eventRepository ;
    private final UsersRepository usersRepository;

    public CreateEventUsecaseImpl(EventRepository eventRepository, UsersRepository usersRepository) {
        this.eventRepository = eventRepository;
        this.usersRepository = usersRepository;
    }

    @Override
    @Transactional
    public CreateEventResponseDTO createEvent(CreateEventRequestDTO req, Long userId) {
        Event newEvent = req.toEntity();
        User userEO = usersRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        newEvent.setOrganizer(userEO);

        try{
            eventRepository.save(newEvent);
        }
        catch (Exception e){
            e.printStackTrace();
        };
        return new CreateEventResponseDTO(newEvent) ;
    }
}
