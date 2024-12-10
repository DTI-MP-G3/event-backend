package com.event.event.infrastructure.coupons.events;


import com.event.event.entity.Point;
import com.event.event.entity.coupons.UserCoupon;
import com.event.event.infrastructure.points.events.ReferralEventReferee;
import com.event.event.usecase.coupon.CreateCouponUsecase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ReferralEventCouponListener {
    CreateCouponUsecase createCouponUsecase;

    public ReferralEventCouponListener(CreateCouponUsecase createCouponUsecase) {
        this.createCouponUsecase = createCouponUsecase;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    public void onApplicationEvent(ReferralEventReferee event) {
        log.info("User with ID {} was refered by {}", event.getReferreeId(), event.getUserId());
        try{
            UserCoupon result = createCouponUsecase.createUserCouponByReferral(event.getReferreeId());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
