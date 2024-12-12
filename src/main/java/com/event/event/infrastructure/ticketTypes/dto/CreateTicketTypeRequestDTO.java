package com.event.event.infrastructure.ticketTypes.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDTO {

    @NotNull
    private Integer eventId;

    @NotNull
    private List<TicketTypeDTO> ticketTypes;


}
