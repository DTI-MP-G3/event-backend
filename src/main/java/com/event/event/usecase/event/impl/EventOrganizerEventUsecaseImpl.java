package com.event.event.usecase.event.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.Event;
import com.event.event.entity.Ticket.TicketType;
import com.event.event.enums.EventStatus;
import com.event.event.enums.TicketTypeStatus;
import com.event.event.infrastructure.events.dto.EventOrganizer.EventSummaryDTO;
import com.event.event.infrastructure.events.dto.EventOrganizer.GetEventResponseDTO;
import com.event.event.infrastructure.events.dto.EventOrganizer.GetEventsResponseDTO;
import com.event.event.infrastructure.events.dto.EventOrganizer.UpdateEventDTO;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.event.EventOrganizerEventUsecase;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@Slf4j
public class EventOrganizerEventUsecaseImpl implements EventOrganizerEventUsecase {

    private final EventRepository eventRepository;
    private final UsersRepository userRepository;
    private final ModelMapper modelMapper;

    public EventOrganizerEventUsecaseImpl(EventRepository eventRepository, UsersRepository userRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


//    GetEventsResponseDTO getAllEvents(Long organizerId);
//    GetEventResponseDTO getEvent(Integer eventId, Long organizerId);
//    GetEventResponseDTO updateEvent(Integer eventId, UpdateEventDTO request, Integer organizerId)
//    void publishEvent(Integer eventId, Long userId);
    @Override
    public GetEventsResponseDTO getAllEvents(Long organizerId){
        List<Event> events = eventRepository.findByOrganizerId(organizerId);
        GetEventsResponseDTO response = new GetEventsResponseDTO();
        response.setOrganizerId(organizerId);
        response.setEvents(events.stream()
                .map(this::convertToEventSummary)
                .collect(Collectors.toList()));
        return response;
    }
    @Override
    public GetEventResponseDTO getEvent(Integer eventId, Long organizerId){
        Event event = eventRepository.findByIdAndOrganizerId(eventId,organizerId).orElseThrow(
                ()-> new DataNotFoundException("Data not found"));
        return modelMapper.map(event, GetEventResponseDTO.class);
    };
    @Override
    @Transactional
    public GetEventResponseDTO updateEvent(Integer eventId, UpdateEventDTO request, Long organizerId){
        Event event = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        event.setName(request.getName());
        event.setVenue(request.getVenue());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setTotalTickets(request.getTotalTickets());
        event.setStatus(request.getStatus());



        try{
            log.info("EO-SERVICE : updating event Save process for id : {}",eventId);
            Event updatedEvent = eventRepository.save(event);
            return modelMapper.map(updatedEvent, GetEventResponseDTO.class);
        }catch (Exception e){
            log.info("EO-SERVICE, UPDATING :{}",e.toString() );
            e.printStackTrace();
            throw new RuntimeException();

        }
    }


    public void publishEvent(Integer eventId, Long organizerId){
        Event event = eventRepository.findByIdAndOrganizerId(eventId, organizerId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));
        event.setStatus(EventStatus.PUBLISHED);
        try{
            Event updatedEvent = eventRepository.save(event);
        }catch (Exception e){
            log.info("EO-SERVICE, PUBLISHING :{}",e.toString() );
            e.printStackTrace();
        }

    }



    private EventSummaryDTO convertToEventSummary(Event event) {
        EventSummaryDTO summary = new EventSummaryDTO();
        summary.setId(event.getId());
        summary.setName(event.getName());
        summary.setVenue(event.getVenue());
        summary.setEventDate(event.getEventDate());
        summary.setStatus(event.getStatus());
        summary.setTotalTickets(event.getTotalTickets());

        // Calculate tickets sold from ticket types
        int ticketsSold = event.getTicketTypes().stream()
                .mapToInt(ticketType -> ticketType.getQuantity() - ticketType.getQuantityAvailable())
                .sum();
        summary.setTicketsSold(ticketsSold);

        return summary;
    }
}
