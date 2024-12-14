package com.event.event.usecase.booking;

import com.event.event.entity.booking.Booking;
import com.event.event.enums.BookingStatus;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface BookingServerUsecase {

    Optional<Booking> getBookingById(Long BookingId);

    void changeBookingStatus (Booking booking, BookingStatus bookingStatus);
}
