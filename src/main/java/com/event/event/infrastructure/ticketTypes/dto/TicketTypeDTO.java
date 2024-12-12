package com.event.event.infrastructure.ticketTypes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDTO {
    @NotNull
    private String name;

    @NotNull
    @Min(0)
    private BigDecimal price;


    private String description;

    @NotNull
    @Min(1)
    private Integer quantity;
}
