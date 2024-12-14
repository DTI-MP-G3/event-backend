package com.event.event.infrastructure.payments.event;

import com.event.event.infrastructure.payments.dto.Server.PaymentResponseSseDTO;
import lombok.Getter;

@Getter
public class PaymentSuccessEvent {
    private final Long paymentId;
    private final PaymentResponseSseDTO response;

    public PaymentSuccessEvent(Long paymentId, PaymentResponseSseDTO response) {
        this.paymentId = paymentId;
        this.response = response;
    }
}
