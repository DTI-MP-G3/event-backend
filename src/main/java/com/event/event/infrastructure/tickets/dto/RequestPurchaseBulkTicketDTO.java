package com.event.event.infrastructure.tickets.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPurchaseBulkTicketDTO {

    @NotNull
    private Integer eventId;

    @NotNull
    List<RequestPurchaseTicketDTO> ticketTypesPurchased;

}
