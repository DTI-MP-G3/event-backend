package com.event.event.entity.coupons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "user_coupons")
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_coupons_id_gen")
    @SequenceGenerator(name = "user_coupons_id_gen", sequenceName = "user_coupons_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name="is_used")
    private Boolean isUsed = false;

    @Column(name = "used_date")
    private OffsetDateTime usedDate;

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
