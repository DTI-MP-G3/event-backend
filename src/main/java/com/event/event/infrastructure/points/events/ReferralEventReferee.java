package com.event.event.infrastructure.points.events;

import org.springframework.context.ApplicationEvent;

public class ReferralEventReferee extends ApplicationEvent {
    private Long userId;
    private Long referreeId;

    public ReferralEventReferee(Object source, Long userId, Long referreeId) {
        super(source);
        this.userId = userId;
        this.referreeId=referreeId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getReferreeId() {return referreeId;}


}
