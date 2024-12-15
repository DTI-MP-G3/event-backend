package com.event.event.usecase.payments.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.entity.booking.Booking;
import com.event.event.entity.payments.Payment;
import com.event.event.enums.BookingStatus;
import com.event.event.enums.PaymentStatus;
import com.event.event.enums.PaymentType;
import com.event.event.infrastructure.bookings.repository.BookingRepository;
import com.event.event.infrastructure.payments.dto.PaymentResponseDTO;
import com.event.event.infrastructure.payments.dto.Server.PaymentResponseServerDTO;
import com.event.event.infrastructure.payments.dto.Server.PaymentResponseSseDTO;
import com.event.event.infrastructure.payments.event.PaymentSuccessEvent;
import com.event.event.infrastructure.payments.repository.PaymentRepository;
import com.event.event.usecase.booking.BookingServerUsecase;
import com.event.event.usecase.booking.impl.BookingServerUsecaseImpl;
import com.event.event.usecase.payments.PaymentUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;


@Service
@Slf4j
public class PaymentUsecaseImpl implements PaymentUsecase {

    private final BookingServerUsecase bookingServerUsecase;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;


    public PaymentUsecaseImpl(BookingServerUsecase bookingServerUsecase,
                              PaymentRepository paymentRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.bookingServerUsecase = bookingServerUsecase;
        this.paymentRepository = paymentRepository;
        this.eventPublisher =eventPublisher;
    }

    @Override
    @Transactional
    public Long createPaymentBillId(Booking booking) {

        Payment newPayment = new Payment();

        newPayment.setBooking(booking);
        newPayment.setPaymentStatus(PaymentStatus.PENDING);

        try{
            log.info("Saving Payment Bill with bookingId : {}", booking.getId());
            Payment savedPayment = paymentRepository.saveAndFlush(newPayment);
            return savedPayment.getId();
        }catch (Exception e){
            log.error("Error saving payment", e);
            throw new RuntimeException("Failed to create payment", e);}

    };

    @Override
    @Transactional
    public Payment payByQr(Long paymentId){
        Payment newPayment = paymentRepository.findById(paymentId).orElseThrow(
                ()->  new DataNotFoundException("No Payment DATA"));

        log.info("Paying with QR payment ID: {}", paymentId);
        newPayment.setPaymentStatus(PaymentStatus.COMPLETED);
        newPayment.setPaymentType(PaymentType.QR);
        newPayment.setPaymentDate(OffsetDateTime.now());
        try{
            bookingServerUsecase.changeBookingStatus(newPayment.getBooking(), BookingStatus.BOOKED);
            Payment savedPayment = paymentRepository.save(newPayment);
            processPayment(savedPayment);
            return newPayment;
        }catch (Exception e){
            log.info("Payment QR Save failed catch: {}",e.toString());
            throw e;
        }

    }

    @Override
    public Payment getPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment Data Not found"));
    }


    @Override
    @Transactional
    public Payment handlePaymentExpired(Long paymentId){
        Payment newPayment  = paymentRepository.findById(paymentId).orElseThrow(() -> new DataNotFoundException("Payment Data Not found"));
        newPayment.setPaymentStatus(PaymentStatus.TIME_EXPIRED);
        Booking cancelledBooking = newPayment.getBooking();
        bookingServerUsecase.changeBookingStatus(cancelledBooking, BookingStatus.PAYMENT_EXPIRED);
        Payment savedPayment = paymentRepository.save(newPayment);
        return savedPayment;
    }


    public void processPayment(Payment payment) {
        log.info("Payment for :{}", payment.getId());
        if (payment.getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            log.info("Completed Payment");
            PaymentResponseSseDTO response = new PaymentResponseSseDTO();
            response.setPaymentId(payment.getId());
            response.setPaymentStatus(PaymentStatus.COMPLETED.toString());
            response.setBookingId(payment.getBooking().getId());
            response.setBookingStatus(payment.getBooking().getStatus().toString());
            eventPublisher.publishEvent(new PaymentSuccessEvent(payment.getId(), response));
        }
    }







}
