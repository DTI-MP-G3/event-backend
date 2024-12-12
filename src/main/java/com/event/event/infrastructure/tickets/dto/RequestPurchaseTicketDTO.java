package com.event.event.infrastructure.tickets.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/*
 event_id Integer
 ticket_type_id long
 user_id long
 qty Integer
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPurchaseTicketDTO {


    @NotNull
    private Long ticketTypeId;

    @NotNull
    @Min(1)
    private Integer quota;

}
