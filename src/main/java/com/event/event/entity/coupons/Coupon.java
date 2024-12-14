package com.event.event.entity.coupons;

import com.event.event.enums.CouponType;
import com.event.event.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "coupons")
@Data
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coupon_id_gen")
    @SequenceGenerator(name = "coupon_id_gen", sequenceName = "coupons_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type",nullable = false)
    @NotNull
    private CouponType couponType = CouponType.GENERAL;

    @Size(max = 20)
    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name="discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name="discount_value")
    private BigDecimal discountValue;

    @Column(name="max_discount_amount")
    private BigDecimal maxDiscountAmount;

    @Column(name="min_purchase_amount")
    private BigDecimal minPurchaseAmount;

    @Column(name="start_date")
    private OffsetDateTime startDate;

    @Column(name="end_date")
    private OffsetDateTime end_date;

    @Column(name = "is_active")
    @ColumnDefault("true")
    private Boolean isActive;

    @Column(name="usage_limit")
    private Integer usageLimit;

    @Column(name= "current_usage_count")
    @ColumnDefault("0")
    private Integer currentUsageCount = 0;

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
