package com.event.event.usecase.booking;

import com.event.event.entity.booking.Booking;
import com.event.event.enums.BookingStatus;
import com.event.event.infrastructure.bookings.dto.BookingRequestDTO;
import com.event.event.infrastructure.bookings.dto.CreateBookingResponseDTO;

public interface CreateBookingUsecase {

    CreateBookingResponseDTO createBooking(BookingRequestDTO dto, Long userId );
}
