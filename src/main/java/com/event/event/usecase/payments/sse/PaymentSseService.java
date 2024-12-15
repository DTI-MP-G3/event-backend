package com.event.event.usecase.payments.sse;

import com.event.event.entity.payments.Payment;
import com.event.event.enums.PaymentStatus;
import com.event.event.infrastructure.payments.dto.Server.PaymentResponseSseDTO;
import com.event.event.infrastructure.payments.event.PaymentSuccessEvent;
import com.event.event.usecase.payments.PaymentUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class PaymentSseService {

    private final PaymentUsecase paymentUsecase;
    public PaymentSseService(PaymentUsecase paymentUsecase) {
        this.paymentUsecase = paymentUsecase;
    }


    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(Long paymentId, SseEmitter emitter) {
        emitters.put(paymentId, emitter);
    }

    public void removeEmitter(Long paymentId) {
        emitters.remove(paymentId);
    }

    @EventListener
    public void handlePaymentSuccess(PaymentSuccessEvent event) {
        SseEmitter emitter = emitters.get(event.getPaymentId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("payment_status")
                        .data(event.getResponse()));
                emitter.complete();
            } catch (IOException e) {
                log.error("Error sending SSE event", e);
                emitter.completeWithError(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                removeEmitter(event.getPaymentId());
            }
        }
    }

    @Transactional
    public void handlePaymentExpired(Long paymentId){
        SseEmitter emitter = emitters.get(paymentId);
        Payment expiredPayment = paymentUsecase.handlePaymentExpired(paymentId);
        PaymentResponseSseDTO response= new PaymentResponseSseDTO();
        response.setPaymentId(paymentId);
        response.setPaymentStatus(expiredPayment.getPaymentStatus().toString());
        response.setBookingId(expiredPayment.getBooking().getId());
        response.setBookingStatus(expiredPayment.getBooking().getStatus().toString());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("payment_status")
                        .data(response));
                emitter.complete();
            } catch (IOException e) {
                log.error("Error sending SSE event", e);
                emitter.completeWithError(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                removeEmitter(expiredPayment.getId());
            }
        }
    }


}
