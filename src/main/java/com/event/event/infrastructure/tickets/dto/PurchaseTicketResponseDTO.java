package com.event.event.infrastructure.tickets.dto;

import com.event.event.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseTicketResponseDTO {
    private String ticketCode;
    private String eventName;
    private String ticketType;
    private OffsetDateTime purchaseDate;
    private TicketStatus status;


}
