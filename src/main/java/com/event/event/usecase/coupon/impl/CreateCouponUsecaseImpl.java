package com.event.event.usecase.coupon.impl;

import com.event.event.entity.coupons.Coupon;
import com.event.event.entity.coupons.UserCoupon;
import com.event.event.enums.DiscountType;
import com.event.event.infrastructure.coupons.repository.CouponRepository;
import com.event.event.infrastructure.coupons.repository.EventCouponRepository;
import com.event.event.infrastructure.coupons.repository.UserCouponRepository;
import com.event.event.infrastructure.coupons.util.CouponCodeService;
import com.event.event.usecase.coupon.CreateCouponUsecase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class CreateCouponUsecaseImpl implements CreateCouponUsecase {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final EventCouponRepository eventCouponRepository;
    private final CouponCodeService couponCodeService;


    public CreateCouponUsecaseImpl(CouponRepository couponRepository
            , UserCouponRepository userCouponRepository
            , EventCouponRepository eventCouponRepository
    ,CouponCodeService couponCodeService) {
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.eventCouponRepository = eventCouponRepository;
        this.couponCodeService = couponCodeService;
    }

    @Override
    @Transactional
    public UserCoupon createUserCouponByReferral(Long userId){
        Coupon newCoupon = new Coupon();
        newCoupon.setCode(couponCodeService.referralCouponGenerator(userId));
        newCoupon.setDiscountType(DiscountType.PERCENTAGE);

        newCoupon.setDiscountValue(new BigDecimal("0.10"));
        newCoupon.setStartDate(OffsetDateTime.now());
        newCoupon.setEnd_date(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(3).withHour(23).withMinute(59).withSecond(59));
        newCoupon.setUsageLimit(1);

        try{
            couponRepository.save(newCoupon);
        }catch (Exception e){
            e.printStackTrace();
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setCoupon(newCoupon);
        userCoupon.setUserId(userId);
        userCoupon.setIsUsed(false);

        try{
            userCouponRepository.save(userCoupon);
        }catch (Exception e){
            e.printStackTrace();
        }

        return userCoupon;
    }
}
