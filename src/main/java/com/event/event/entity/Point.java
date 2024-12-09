package com.event.event.entity;

import com.event.event.enums.PointStatus;
import com.event.event.enums.RewardStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

@Data
@Entity
@Table(name="points_users")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "points_users_id_gen")
    @SequenceGenerator(name = "points_users_id_gen", sequenceName = "points_users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name="points_amount", nullable = false)
    private Integer points;


    @Column(name = "point_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus = PointStatus.ACTIVE;

    @NotNull
    @Column(name = "expires_at", nullable = false)
    private OffsetDateTime expiresAt;

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
        expiresAt = OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(3).withHour(23).withMinute(59).withSecond(59);;
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
