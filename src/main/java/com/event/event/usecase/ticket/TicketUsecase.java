package com.event.event.usecase.ticket;

import com.event.event.infrastructure.tickets.dto.User.UserEventTicketsDTO;

import java.util.List;

public interface TicketUsecase {
    List<UserEventTicketsDTO> getUserTickets(Long userId);
}
