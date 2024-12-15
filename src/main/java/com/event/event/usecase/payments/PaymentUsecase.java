package com.event.event.usecase.payments;

import com.event.event.entity.booking.Booking;
import com.event.event.entity.payments.Payment;
import com.event.event.infrastructure.payments.dto.PaymentResponseDTO;
import com.event.event.infrastructure.payments.dto.Server.PaymentResponseServerDTO;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface PaymentUsecase {
    //Payment Check
    //Payment Paid
    //Payment QR GENERATOR


//    Optional<Payment> findPaymentBillByBookingId(Long bookingId);
    Long createPaymentBillId(Booking booking);


//    paymentByQR
    Payment payByQr(Long paymentId);

    Payment getPaymentById(Long paymentId);

    Payment handlePaymentExpired(Long PaymentId);

}
