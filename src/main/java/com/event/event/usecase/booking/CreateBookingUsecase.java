package com.event.event.usecase.booking;

import com.event.event.infrastructure.bookings.dto.BookingRequestDTO;
import com.event.event.infrastructure.bookings.dto.BookingResponseDTO;

public interface CreateBookingUsecase {

    BookingResponseDTO createBooking(BookingRequestDTO dto, Long userId );
}
