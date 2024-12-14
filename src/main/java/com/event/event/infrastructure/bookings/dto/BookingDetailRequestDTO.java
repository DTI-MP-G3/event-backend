package com.event.event.infrastructure.bookings.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailRequestDTO {

    private Long ticketTypeId;
    private Integer quantity;

}
