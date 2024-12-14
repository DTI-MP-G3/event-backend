package com.event.event.usecase.ticketType.impl;

import com.event.event.entity.Ticket.TicketType;
import com.event.event.infrastructure.ticketTypes.repository.TicketTypeRepository;
import com.event.event.usecase.ticketType.TicketTypeUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TicketTypeUsecaseImpl implements TicketTypeUsecase {

    private final TicketTypeRepository ticketTypeRepository;

    public TicketTypeUsecaseImpl(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    @Transactional
    public void decreaseTicketAvailability(TicketType ticketType, int amount) {
        ticketType.decreaseAvailable(amount);
        ticketTypeRepository.save(ticketType);
        log.info("Decreased ticket availability for type {} by {}", ticketType.getId(), amount);
    }

    @Override
    @Transactional
    public void increaseTicketAvailability(TicketType ticketType, int amount) {
        ticketType.increaseAvailable(amount);
        ticketTypeRepository.save(ticketType);
    }
}
