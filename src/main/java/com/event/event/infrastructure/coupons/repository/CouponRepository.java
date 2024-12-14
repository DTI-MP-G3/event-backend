package com.event.event.infrastructure.coupons.repository;

import com.event.event.entity.coupons.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Optional<Coupon> findByCodeIgnoreCase(String code);
}
