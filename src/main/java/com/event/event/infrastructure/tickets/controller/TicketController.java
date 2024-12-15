package com.event.event.infrastructure.tickets.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.security.Claims;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseBulkTicketDTO;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseTicketDTO;
import com.event.event.usecase.ticket.PurchaseTicketUsecase;
import com.event.event.usecase.ticket.TicketUsecase;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

    private final PurchaseTicketUsecase purchaseTicketUsecase;
    private final TicketUsecase ticketUsecase;


    public TicketController(PurchaseTicketUsecase purchaseTicketUsecase, TicketUsecase ticketUsecase) {
        this.purchaseTicketUsecase = purchaseTicketUsecase;
        this.ticketUsecase = ticketUsecase;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostMapping("/bulk-ticket")
    public ResponseEntity<?> purchaseBulkTicket(@RequestBody RequestPurchaseBulkTicketDTO req){
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Ticket Purchase BUlk Sucess", purchaseTicketUsecase.PurchaseBulkTicket(req.getEventId(),userId,req.getTicketTypesPurchased()));

    }
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostMapping("/purchase/{eventId}")
    public ResponseEntity<?> purchaseTicket(@RequestBody RequestPurchaseTicketDTO req, @PathVariable("eventId") Integer eventId){
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Ticket Purchase BUlk Sucess", purchaseTicketUsecase.PurchaseTicket (eventId,userId,req));

    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/user-tickets")
    public ResponseEntity<?> getUserTickets() {
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("User Ticket", ticketUsecase.getUserTickets(userId));
    }
}
