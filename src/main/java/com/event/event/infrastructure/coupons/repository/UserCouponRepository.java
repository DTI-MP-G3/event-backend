package com.event.event.infrastructure.coupons.repository;


import com.event.event.entity.coupons.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserId(Long userId);
}
