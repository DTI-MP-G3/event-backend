package com.event.event.usecase.ticketType.impl;

import com.event.event.entity.Ticket.TicketType;
import com.event.event.infrastructure.ticketTypes.repository.TicketTypeRepository;
import com.event.event.usecase.ticketType.SearchTicketTypeUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class SearchTicketTypeUsecaseImpl implements SearchTicketTypeUsecase {
    private final TicketTypeRepository ticketTypeRepository;

    public SearchTicketTypeUsecaseImpl(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public List<TicketType> findAllTicketTypesById(Iterable<Long> ids){
        return ticketTypeRepository.findAllById(ids);
    }

    public List<TicketType> findAllTicketTypesByEventId(Integer eventId){
        log.info("Search by EventId : {}" ,eventId);
        return  ticketTypeRepository.findByEventId(eventId);
    }
}
