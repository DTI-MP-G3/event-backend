package com.event.event.infrastructure.tickets.event;

import com.event.event.entity.payments.Payment;
import com.event.event.infrastructure.payments.event.PaymentSuccessEvent;
import com.event.event.usecase.ticket.PurchaseTicketUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j

@Component
public class TicketEventListener {

    PurchaseTicketUsecase purchaseTicketUsecase;

    public TicketEventListener(PurchaseTicketUsecase purchaseTicketUsecase) {
        this.purchaseTicketUsecase = purchaseTicketUsecase;
    };

    @EventListener
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(PaymentSuccessEvent event) {
        log.info("listen to payment success event");
        Long bookingId = event.getResponse().getBookingId();
        purchaseTicketUsecase.generateTicketsFromBooking(bookingId);
    }

}
