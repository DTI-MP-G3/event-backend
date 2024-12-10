package com.event.event.infrastructure.coupons.repository;

import com.event.event.entity.coupons.EventCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventCouponRepository extends JpaRepository<EventCoupon,Long> {
    List<EventCoupon> findByEventId(Long eventId);
}
