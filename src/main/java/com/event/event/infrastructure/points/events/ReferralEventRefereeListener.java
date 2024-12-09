package com.event.event.infrastructure.points.events;

import com.event.event.entity.Point;
import com.event.event.usecase.point.ReferralPointUsecase;
import com.event.event.usecase.point.impl.ReferralPointUsecaseImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ReferralEventRefereeListener  {

    ReferralPointUsecase referralPointUsecase;

    public ReferralEventRefereeListener(ReferralPointUsecase referralPointUsecase) {
        this.referralPointUsecase = referralPointUsecase;
    }

    @EventListener
    @Async
    public void onApplicationEvent(ReferralEventReferee event) {
        log.info("User with ID {} was refer someone", event.getUserId());
        try{
            Point result = referralPointUsecase.addReferralPointByRegister(event.getUserId());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
