package com.event.event.usecase.coupon;

import com.event.event.entity.coupons.UserCoupon;

public interface CreateCouponUsecase {

    UserCoupon createUserCouponByReferral(Long userId);
}
