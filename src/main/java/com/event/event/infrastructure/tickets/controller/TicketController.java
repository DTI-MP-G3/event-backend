package com.event.event.infrastructure.tickets.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.security.Claims;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseBulkTicketDTO;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseTicketDTO;
import com.event.event.usecase.ticket.PurchaseTicketUsecase;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {

    private final PurchaseTicketUsecase purchaseTicketUsecase;

    public TicketController(PurchaseTicketUsecase purchaseTicketUsecase) {
        this.purchaseTicketUsecase = purchaseTicketUsecase;
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
}
