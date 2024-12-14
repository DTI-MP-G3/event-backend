package com.event.event.usecase.coupon.impl;

import com.event.event.entity.coupons.Coupon;
import com.event.event.entity.coupons.EventCoupon;
import com.event.event.entity.coupons.UserCoupon;
import com.event.event.infrastructure.coupons.repository.CouponRepository;
import com.event.event.infrastructure.coupons.repository.EventCouponRepository;
import com.event.event.infrastructure.coupons.repository.UserCouponRepository;
import com.event.event.usecase.coupon.SearchCouponUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
public class SearchCouponUsecaseImpl implements SearchCouponUsecase {


    private CouponRepository couponRepository;
    private UserCouponRepository userCouponRepository;

    private EventCouponRepository eventCouponRepository;

    public SearchCouponUsecaseImpl(CouponRepository couponRepository,
                                   UserCouponRepository userCouponRepository,
                                   EventCouponRepository eventCouponRepository) {
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.eventCouponRepository = eventCouponRepository;
    }

    @Override
    public Optional<UserCoupon> findUserCouponByIdAndUserId (Long couponId, Long userId){
        log.info("search event coupon with User id : {}  and coupon id : {}", couponId, userId);
        return userCouponRepository.findByUserIdAndCoupon_Id(userId,couponId);
    };

    @Override
    public Optional<Coupon> findById(Long id){
        return couponRepository.findById(id);
    }

    @Override
    public Optional<EventCoupon> findEventCouponByCouponIdAndEventId(Long couponId, Integer eventId){
        log.info("search event coupon with event id : {}  and coupon id : {}", couponId, eventId);
        return eventCouponRepository.findByEventIdAndCouponId(eventId,couponId);
    }


}
