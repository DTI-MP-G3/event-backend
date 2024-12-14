package com.event.event.usecase.booking.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.booking.Booking;
import com.event.event.enums.BookingStatus;
import com.event.event.infrastructure.bookings.repository.BookingRepository;
import com.event.event.usecase.booking.BookingServerUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class BookingServerUsecaseImpl implements BookingServerUsecase {

    BookingRepository bookingRepository;

    public BookingServerUsecaseImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    @Override
    public Optional<Booking> getBookingById(Long BookingId){
        Optional<Booking> booking = bookingRepository.findById(BookingId);
        if(booking.isEmpty()){
            log.info("Booking data not found with this id: {}",BookingId);
            throw new DataNotFoundException("Booking not found");
        }
        return booking;
    }

    @Transactional
    @Override
    public void changeBookingStatus (Booking booking, BookingStatus bookingStatus){
        booking.setStatus(bookingStatus);
        try{
            bookingRepository.save(booking);
        }catch (Exception e){
            log.info("BookingService: Caught : {} ",e.toString());
        }
        return ;
    }

}
