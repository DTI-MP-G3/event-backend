package com.event.event.usecase.ticketType;

import com.event.event.entity.Ticket.TicketType;
import jakarta.transaction.Transactional;

public interface TicketTypeUsecase {

     void decreaseTicketAvailability(TicketType ticketType, int amount);
     void increaseTicketAvailability(TicketType ticketType, int amount);
}
