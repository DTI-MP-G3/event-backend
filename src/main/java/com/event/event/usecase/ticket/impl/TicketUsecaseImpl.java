package com.event.event.usecase.ticket.impl;

import com.event.event.entity.Ticket.Ticket;
import com.event.event.infrastructure.tickets.dto.User.TicketDetailDTO;
import com.event.event.infrastructure.tickets.dto.User.UserEventTicketsDTO;
import com.event.event.infrastructure.tickets.repository.TicketRepository;
import com.event.event.usecase.ticket.TicketUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TicketUsecaseImpl implements TicketUsecase {

    TicketRepository ticketRepository;

    public TicketUsecaseImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<UserEventTicketsDTO> getUserTickets(Long userId) {
        List<Ticket> tickets = ticketRepository.findByUserId(userId);

        log.info("Searching ticket for user : {}",userId);

        return tickets.stream()
                .collect(Collectors.groupingBy(
                        Ticket::getEvent,
                        Collectors.groupingBy(
                                ticket -> ticket.getTicketType().getName(),
                                Collectors.mapping(this::toTicketDetailDTO, Collectors.toList())
                        )
                ))
                .entrySet().stream()
                .map(entry -> UserEventTicketsDTO.builder()
                        .eventId(entry.getKey().getId())
                        .eventName(entry.getKey().getName())
                        .eventDate(entry.getKey().getEventDate())
                        .eventLocation(entry.getKey().getVenue())
                        .eventStatus(entry.getKey().getStatus().name())
                        .ticketsByType(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private TicketDetailDTO toTicketDetailDTO(Ticket ticket) {
        return TicketDetailDTO.builder()
                .ticketCode(ticket.getTicketCode())
                .status(ticket.getStatus())
                .purchaseDate(ticket.getPurchaseDate())
                .price(ticket.getTicketType().getPrice())
                .build();
    }
}

