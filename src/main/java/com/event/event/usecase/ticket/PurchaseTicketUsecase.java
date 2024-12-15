package com.event.event.usecase.ticket;

import com.event.event.entity.Ticket.Ticket;
import com.event.event.infrastructure.tickets.dto.PurchaseTicketResponseDTO;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseBulkTicketDTO;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseTicketDTO;

import java.util.List;

public interface PurchaseTicketUsecase {

    List<PurchaseTicketResponseDTO> PurchaseBulkTicket(Integer eventId, Long userId, List<RequestPurchaseTicketDTO> ticketDtos);

    List<PurchaseTicketResponseDTO>  PurchaseTicket(Integer eventId, Long userId, RequestPurchaseTicketDTO ticketDto);
    List<PurchaseTicketResponseDTO> generateTicketsFromBooking(Long bookingId);



}
