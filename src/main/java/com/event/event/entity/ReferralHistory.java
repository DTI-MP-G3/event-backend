package com.event.event.entity;

import com.event.event.enums.RewardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "referral_history")
public class ReferralHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_history_id_gen")
    @SequenceGenerator(name = "referral_history_id_gen", sequenceName = "referral_history_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "referrer_id", nullable = false)
    private Long referrerId;

    @Column(name = "referee_id", nullable = false)
    private Long refereeId;

    @Column(name = "reward_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardStatus rewardStatus = RewardStatus.PENDING;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }


}
