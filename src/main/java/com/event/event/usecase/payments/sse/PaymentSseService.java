package com.event.event.usecase.payments.sse;

import com.event.event.infrastructure.payments.event.PaymentSuccessEvent;
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
}
