package com.event.event.usecase.ticketType.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.common.exception.ForbiddenException;
import com.event.event.common.exception.InvalidEventStateException;
import com.event.event.entity.Event;
import com.event.event.entity.Ticket.TicketType;
import com.event.event.enums.EventStatus;
import com.event.event.enums.TicketTypeStatus;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.infrastructure.ticketTypes.dto.TicketTypeDTO;
import com.event.event.infrastructure.ticketTypes.repository.TicketTypeRepository;
import com.event.event.usecase.ticketType.CreateTicketTypeUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CreateTicketTypeUsecaseImpl implements CreateTicketTypeUsecase {

    TicketTypeRepository ticketTypeRepository;
    EventRepository eventRepository;

    public CreateTicketTypeUsecaseImpl(TicketTypeRepository ticketTypeRepository, EventRepository eventRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
        this.eventRepository = eventRepository;
    }



    @Transactional
    @Override
    public List<TicketType> createBulkTicketTypes(Integer eventId, List<TicketTypeDTO> dtos, Long organizerId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event Not found"));

        validateOrganizer(event, organizerId);
        validateEventStatus(event);

        List<TicketType> ticketTypes = dtos.stream()
                .map(dto -> {
                    TicketType type = new TicketType();
                    type.setEvent(event);
                    mapTicketTypeFromDTO(type, dto);
                    return type;
                })
                .collect(Collectors.toList());

        return ticketTypeRepository.saveAll(ticketTypes);
    }

    @Transactional
    @Override
    public TicketType createTicketTypes(Integer eventId, TicketTypeDTO dto, Long organizerId){
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event Not found"));

        validateOrganizer(event, organizerId);
        validateEventStatus(event);
        TicketType type = new TicketType();
        type.setEvent(event);
        mapTicketTypeFromDTO(type,dto);
        try{
            ticketTypeRepository.save(type);
        }catch (Exception e){
            throw new RuntimeException();
        }

        return type;
    }


    private void validateOrganizer(Event event, Long organizerId) {
        if (!event.getOrganizer().getId().equals(organizerId)) {
            throw new ForbiddenException("Not authorized to modify this event's tickets");
        }
    }

    private void validateEventStatus(Event event) {
        if (event.getStatus() != EventStatus.DRAFT && event.getStatus() != EventStatus.PUBLISHED) {
            throw new InvalidEventStateException("Cannot modify tickets for this event status");
        }
    }


    private void mapTicketTypeFromDTO(TicketType ticketType, TicketTypeDTO dto) {
        ticketType.setName(dto.getName());
        ticketType.setPrice(dto.getPrice());
        ticketType.setQuantity(dto.getQuantity());
        ticketType.setQuantityAvailable(dto.getQuantity());
        ticketType.setDescription(dto.getDescription());
        ticketType.setStatus(TicketTypeStatus.AVAILABLE);
    }
}
