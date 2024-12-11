package com.event.event.infrastructure.tickets.util;

import org.springframework.stereotype.Component;

@Component
public class TicketCodeGenerator {
    private String generateTicketCode(Long eventId, Long ticketTypeId, Long ticketId) {
        return String.format("EV%d-TT%d-TK%d", eventId, ticketTypeId, ticketId);
    }
}
