package com.event.event.usecase.coupon;

import com.event.event.entity.coupons.Coupon;
import com.event.event.entity.coupons.EventCoupon;
import com.event.event.entity.coupons.UserCoupon;

import java.util.Optional;

public interface SearchCouponUsecase {

    Optional<UserCoupon> findUserCouponByIdAndUserId (Long id, Long userId);
    Optional<Coupon> findById(Long id);
    Optional<EventCoupon> findEventCouponByCouponIdAndEventId(Long couponId, Integer eventId);
}
