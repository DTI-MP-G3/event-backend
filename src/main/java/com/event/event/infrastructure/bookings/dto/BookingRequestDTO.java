package com.event.event.infrastructure.bookings.dto;

import com.event.event.entity.booking.Booking;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull
    private Integer eventId;

    @NotNull
    private Long couponId;
    @NotNull
    private Long pointId;

    @NotNull
    private List<BookingDetailRequestDTO> bookingDetails;


}
