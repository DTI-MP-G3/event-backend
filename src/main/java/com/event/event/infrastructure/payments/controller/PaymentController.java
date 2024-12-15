package com.event.event.infrastructure.payments.controller;


import com.event.event.common.exception.ForbiddenException;
import com.event.event.common.response.ApiResponse;
import com.event.event.entity.payments.Payment;
import com.event.event.infrastructure.security.Claims;
import com.event.event.usecase.payments.PaymentUsecase;
import com.event.event.usecase.payments.sse.PaymentSseService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

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

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping(value="/stream/{paymentId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamPaymentStatus(@PathVariable Long paymentId) {
        Payment payment = paymentUsecase.getPaymentById(paymentId);
        Long userId = Claims.getUserIdFromJwt();

        if (!payment.getBooking().getUser().getId().equals(userId)) {
            throw new ForbiddenException("Unauthorized to access this payment stream");
        }

        long timeout = ChronoUnit.MILLIS.between(OffsetDateTime.now(), payment.getExpiryDate());
        SseEmitter emitter = new SseEmitter(timeout);
        paymentSseService.addEmitter(paymentId, emitter);

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected to payment stream for ID: " + paymentId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        emitter.onCompletion(() -> paymentSseService.removeEmitter(paymentId));
        emitter.onTimeout(() -> paymentSseService.handlePaymentExpired(paymentId));

        return emitter;
    }
}
