package com.event.event.infrastructure.payments.controller;


import com.event.event.common.response.ApiResponse;
import com.event.event.usecase.payments.PaymentUsecase;
import com.event.event.usecase.payments.sse.PaymentSseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private PaymentSseService paymentSseService;
    private PaymentUsecase paymentUsecase;

    public PaymentController(PaymentSseService paymentSseService, PaymentUsecase paymentUsecase) {
        this.paymentSseService = paymentSseService;
        this.paymentUsecase = paymentUsecase;
    }

    @PostMapping("/QR/{paymentId}")
    public ResponseEntity<?> payQr (@PathVariable("paymentId") Long paymentId){
        paymentUsecase.payByQr(paymentId);
        return ApiResponse.successfulResponse("Yeay PAID {}", paymentId);
    }

    @GetMapping(value="/stream/{paymentId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPaymentStatus(@PathVariable Long paymentId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        paymentSseService.addEmitter(paymentId, emitter);

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected to payment stream for ID: " + paymentId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        emitter.onCompletion(() -> paymentSseService.removeEmitter(paymentId));
        emitter.onTimeout(() -> paymentSseService.removeEmitter(paymentId));

        return emitter;
    }
}
