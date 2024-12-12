package com.event.event.usecase.ticketType;

import com.event.event.entity.Ticket.TicketType;
import com.event.event.infrastructure.ticketTypes.dto.TicketTypeDTO;

import java.util.List;

public interface CreateTicketTypeUsecase {

    List<TicketType> createBulkTicketTypes(Integer eventId, List<TicketTypeDTO> dtos, Long organizerId);
    TicketType createTicketTypes(Integer eventId, TicketTypeDTO dto, Long organizerId);
}
