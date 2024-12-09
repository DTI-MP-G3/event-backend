package com.event.event.usecase.referrals;

import com.event.event.entity.ReferralHistory;

public interface ReferralHistoryUsecase {
    ReferralHistory createReferralHistory(Long referrerId, Long refereeId);
}
