package com.event.event.infrastructure.tickets.dto.User;

import com.event.event.enums.TicketStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
public class TicketDetailDTO {
    private String ticketCode;
    private TicketStatus status;
    private OffsetDateTime purchaseDate;
    private BigDecimal price;
}