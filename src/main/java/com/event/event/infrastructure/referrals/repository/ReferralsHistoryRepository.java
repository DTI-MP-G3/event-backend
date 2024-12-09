package com.event.event.infrastructure.referrals.repository;

import com.event.event.entity.ReferralHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralsHistoryRepository extends JpaRepository<ReferralHistory, Long> {
}
