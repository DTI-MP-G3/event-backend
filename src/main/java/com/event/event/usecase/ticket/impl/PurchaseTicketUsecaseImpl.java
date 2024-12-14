package com.event.event.usecase.ticket.impl;


import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.Event;
import com.event.event.entity.Ticket.Ticket;
import com.event.event.entity.Ticket.TicketType;
import com.event.event.entity.User;
import com.event.event.enums.TicketStatus;
import com.event.event.infrastructure.events.repository.EventRepository;
import com.event.event.infrastructure.ticketTypes.repository.TicketTypeRepository;
import com.event.event.infrastructure.tickets.dto.PurchaseTicketResponseDTO;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseTicketDTO;
import com.event.event.infrastructure.tickets.repository.TicketRepository;
import com.event.event.infrastructure.tickets.util.TicketCodeGenerator;
import com.event.event.infrastructure.users.repository.UsersRepository;
import com.event.event.usecase.ticket.PurchaseTicketUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PurchaseTicketUsecaseImpl implements PurchaseTicketUsecase {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    private final TicketTypeRepository ticketTypeRepository;
    private final UsersRepository usersRepository;
    private final TicketCodeGenerator ticketCodeGenerator;

    public PurchaseTicketUsecaseImpl(EventRepository eventRepository, TicketRepository ticketRepository, TicketTypeRepository ticketTypeRepository, UsersRepository usersRepository, TicketCodeGenerator ticketCodeGenerator) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.ticketTypeRepository = ticketTypeRepository;
        this.usersRepository = usersRepository;
        this.ticketCodeGenerator = ticketCodeGenerator;
    }

    @Override
    @Transactional
    public List<PurchaseTicketResponseDTO> PurchaseBulkTicket(Integer eventId, Long userId, List<RequestPurchaseTicketDTO> ticketDtos){
        Event event= eventRepository.findById(eventId).orElseThrow(()->{
            log.info("PurchaseTicketService: EventId not found with id : {} ",eventId);
            return new DataNotFoundException("Event not found");});
        User user = usersRepository.findById(userId).orElseThrow(()->{
            log.info("PurchaseTicketService : UserId not found with id: {}", userId);
            return new DataNotFoundException("User not Found");
        });

        Set<Long> ticketTypeIds = ticketDtos.stream()
                .map(RequestPurchaseTicketDTO::getTicketTypeId)
                .collect(Collectors.toSet());

        List<TicketType> ticketTypes = ticketTypeRepository.findAllById(ticketTypeIds);

        if(ticketTypes.size() != ticketTypeIds.size()){
            throw new DataNotFoundException("ticket type not valid");
        }

        Map<Long, TicketType> ticketTypeMap = ticketTypes.stream().collect(Collectors.toMap(TicketType::getId,type->type));


        List<Ticket> newTickets = new ArrayList<>();
        Long lastId = ticketRepository.getMaxId();
        for(RequestPurchaseTicketDTO dto: ticketDtos){
            for (int i = 0 ; i < dto.getQuota() ; i++){
                TicketType newTicketType = ticketTypeMap.get(dto.getTicketTypeId());
                Long nextId = lastId + i+1;
                String newTicketCode= ticketCodeGenerator.generateTicketCode(eventId, dto.getTicketTypeId(),nextId);
                Ticket ticket= Ticket.builder()
                        .ticketType(newTicketType)
                        .user(user)
                        .event(event)
                        .status(TicketStatus.AVAILABLE)
                        .purchaseDate(OffsetDateTime.now())
                        .ticketCode(newTicketCode)
                        .build();
                newTickets.add(ticket);
            }
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(newTickets);
        return savedTickets.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());


    };

    @Override
    @Transactional
    public List<PurchaseTicketResponseDTO> PurchaseTicket(Integer eventId, Long userId, RequestPurchaseTicketDTO ticketDto){
        Event event= eventRepository.findById(eventId).orElseThrow(()->{
            log.info("PurchaseTicketService: EventId not found with id : {} ",eventId);
            return new DataNotFoundException("Event not found");});
        User user = usersRepository.findById(userId).orElseThrow(()->{
            log.info("PurchaseTicketService : UserId not found with id: {}", userId);
            return new DataNotFoundException("User not Found");
        });
        TicketType ticketType = ticketTypeRepository.findById(ticketDto.getTicketTypeId()).orElseThrow(()->{
            log.info("PurchaseTicketService : TicketType not found with id: {}", ticketDto.getTicketTypeId());
            return new DataNotFoundException("Ticket Type not Found");
        });
        Long lastId = ticketRepository.getMaxId();

        List<Ticket> newTickets = new ArrayList<>();
        for (int i = 0 ; i < ticketDto.getQuota() ; i++){
            Long nextId = lastId + i+1;
            String newTicketCode= ticketCodeGenerator.generateTicketCode(eventId, ticketDto.getTicketTypeId(),nextId);
            Ticket ticket= Ticket.builder()
                    .ticketType(ticketType)
                    .user(user)
                    .event(event)
                    .status(TicketStatus.AVAILABLE)
                    .purchaseDate(OffsetDateTime.now())
                    .ticketCode(newTicketCode)
                    .build();
            newTickets.add(ticket);
        }

        List<Ticket> savedTickets = ticketRepository.saveAll(newTickets);
        return savedTickets.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    };



    private PurchaseTicketResponseDTO mapToDTO(Ticket ticket) {
        return PurchaseTicketResponseDTO.builder()
                .ticketCode(ticket.getTicketCode())
                .eventName(ticket.getEvent().getName())
                .ticketType(ticket.getTicketType().getName())
                .purchaseDate(ticket.getPurchaseDate())
                .status(ticket.getStatus())
                .build();
    }
}
