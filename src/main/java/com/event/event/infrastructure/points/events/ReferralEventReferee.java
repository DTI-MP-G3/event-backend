package com.event.event.infrastructure.points.events;

import org.springframework.context.ApplicationEvent;

public class ReferralEventReferee extends ApplicationEvent {
    private Long userId;

    public ReferralEventReferee(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
