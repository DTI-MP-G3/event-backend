package com.event.event.infrastructure.coupons.repository;


import com.event.event.entity.Point;
import com.event.event.entity.coupons.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByUserId(Long userId);
    Optional<UserCoupon> findByUserIdAndCoupon_Id(Long userId, Long couponId);
}
