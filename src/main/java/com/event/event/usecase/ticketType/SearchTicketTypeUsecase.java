package com.event.event.usecase.ticketType;

import com.event.event.entity.Ticket.TicketType;

import java.util.List;

public interface SearchTicketTypeUsecase {
    List<TicketType> findAllTicketTypesById(Iterable<Long> ids);
}
