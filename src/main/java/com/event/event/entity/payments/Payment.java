package com.event.event.entity.payments;


import com.event.event.entity.booking.Booking;
import com.event.event.enums.PaymentStatus;
import com.event.event.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


//  id BIGSERIAL PRIMARY KEY,
//          booking_id BIGINT REFERENCES bookings(id),
//          amount DECIMAL(10,2) NOT NULL,
//          payment_type VARCHAR(20) NOT NULL,
//          payment_status VARCHAR(20) NOT NULL,
//          transaction_id VARCHAR(100) UNIQUE,
//          payment_date TIMESTAMP WITH TIME ZONE,
//          expiry_date TIMESTAMP WITH TIME ZONE,
//          created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
//          updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
//          deleted_at TIMESTAMP WITH TIME ZONE,
//          CONSTRAINT check_payment_type CHECK (payment_type IN ('CREDIT_CARD', 'DEBIT_CARD', 'BANK_TRANSFER', 'E_WALLET', 'POINTS','QR')),
//          CONSTRAINT check_payment_status CHECK (payment_status IN ('PENDING', 'PROCESSING', 'COMPLETED', 'FAILED', 'REFUNDED', 'TIME_EXPIRED'))

@Entity
@Getter
@Setter
@Data
@Table(name="payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_id_gen")
    @SequenceGenerator(name = "payment_id_gen", sequenceName = "payments_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name="amount", precision = 10, scale =2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(name ="payment_status", nullable = false)
    PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name="transaction_id", nullable = false,unique = true)
    String transactionId;

    @Column(name = "payment_date")
    private OffsetDateTime paymentDate;

    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
        expiryDate= OffsetDateTime.now().plusMinutes(15);
        transactionId = generateTransactionId();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }

    private String generateTransactionId(){
        String transactionId = String.format("TRX-%d-%s",
                booking.getId(),
                OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        );
        return transactionId;
    }
}
