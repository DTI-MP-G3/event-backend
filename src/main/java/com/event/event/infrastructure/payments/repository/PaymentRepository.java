package com.event.event.infrastructure.payments.repository;

import com.event.event.entity.payments.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByBookingId(Long bookingId);
}
