package com.event.event.infrastructure.bookings.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.infrastructure.bookings.dto.BookingRequestDTO;
import com.event.event.infrastructure.security.Claims;
import com.event.event.infrastructure.tickets.dto.RequestPurchaseBulkTicketDTO;
import com.event.event.usecase.booking.CreateBookingUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/booking")
public class BookingController {

    CreateBookingUsecase createBookingUsecase;

    public BookingController(CreateBookingUsecase createBookingUsecase) {
        this.createBookingUsecase = createBookingUsecase;
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @PostMapping
    public ResponseEntity<?> purchaseBulkTicket(@RequestBody BookingRequestDTO req){
        Long userId = Claims.getUserIdFromJwt();
        return ApiResponse.successfulResponse("Booking Sucess", createBookingUsecase.createBooking(req,userId));

    }

}
