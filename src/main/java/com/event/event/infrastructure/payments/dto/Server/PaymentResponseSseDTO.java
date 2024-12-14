package com.event.event.infrastructure.payments.dto.Server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseSseDTO {
    Long paymentId;
    Long bookingId;

    String bookingStatus;
    String paymentStatus;

}
