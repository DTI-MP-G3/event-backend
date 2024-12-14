package com.event.event.infrastructure.coupons.repository;

import com.event.event.entity.coupons.EventCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventCouponRepository extends JpaRepository<EventCoupon,Long> {
    List<EventCoupon> findByEventId(Long eventId);
    Optional<EventCoupon> findByEventIdAndCouponId(Integer eventId, Long couponId);
}
