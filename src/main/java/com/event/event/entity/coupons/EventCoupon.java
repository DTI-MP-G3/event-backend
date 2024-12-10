package com.event.event.entity.coupons;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;


@Data
@Entity
@Table(name = "event_coupons")
public class EventCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_coupon_id_gen")
    @SequenceGenerator(name = "event_coupon_id_gen", sequenceName = "event_coupon_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @NotNull
    @Column(name="event_id")
    private Long eventId;

    @NotNull
    @Column(name="organizer_id")
    private Long organizerId;

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
