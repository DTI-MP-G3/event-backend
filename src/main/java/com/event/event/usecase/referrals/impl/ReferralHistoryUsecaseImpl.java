package com.event.event.usecase.referrals.impl;

import com.event.event.entity.ReferralHistory;
import com.event.event.infrastructure.referrals.repository.ReferralsHistoryRepository;
import com.event.event.usecase.referrals.ReferralHistoryUsecase;
import org.springframework.stereotype.Service;

@Service
public class ReferralHistoryUsecaseImpl implements ReferralHistoryUsecase {
    ReferralsHistoryRepository referralsHistoryRepository;

    public ReferralHistoryUsecaseImpl(ReferralsHistoryRepository referralsHistoryRepository) {
        this.referralsHistoryRepository = referralsHistoryRepository;
    }

    public ReferralHistory createReferralHistory(Long referrerId, Long refereeId) {
        ReferralHistory referralHistory = new ReferralHistory();
        referralHistory.setReferrerId(referrerId);
        referralHistory.setRefereeId(refereeId);
        referralsHistoryRepository.save(referralHistory);
        return referralHistory;
    }
}

