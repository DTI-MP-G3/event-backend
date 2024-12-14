package com.event.event.entity.booking;

import com.event.event.entity.Event;
import com.event.event.entity.Point;
import com.event.event.entity.User;
import com.event.event.entity.coupons.Coupon;
import com.event.event.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_gen")
        @SequenceGenerator(name = "booking_id_gen", sequenceName = "bookings_id_seq", allocationSize = 1)
        @Column(name = "id", nullable = false)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;


        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "event_id", nullable = false)
        private Event event;

        @Column(name = "booking_date")
        private OffsetDateTime bookingDate;

        @Enumerated(EnumType.STRING)
        @Column(name = "status",nullable = false)
        private BookingStatus status = BookingStatus.RESERVED;

        @Column(name="subtotal")
        private BigDecimal subtotal;

        @Column(name = "booking_number", nullable = false)
        private String bookingNumber ;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "coupon_id")
        private Coupon coupon;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn (name = "point_id")
        private Point point;

        @Column(name = "fee_amount", precision = 10, scale = 2)
        private BigDecimal feeAmount;

        @Column(name = "tax_amount", precision = 10, scale = 2)
        private BigDecimal taxAmount;

        @Column(name = "discount_amount", precision = 10, scale = 2)
        private BigDecimal discountAmount;

        @Column(name = "point_amount", precision = 10, scale = 2)
        private BigDecimal pointAmount;

        @Column(name = "total_amount", precision = 10, scale = 2)
        private BigDecimal totalAmount;

        @Column(name = "created_at")
        private OffsetDateTime createdAt;

        @Column(name = "updated_at")
        private OffsetDateTime updatedAt;

        @Column(name = "deleted_at")
        private OffsetDateTime deletedAt;


        @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
        private List<BookingDetail> bookingDetails = new ArrayList<>();

        @PrePersist
        protected void onCreate() {
            createdAt = OffsetDateTime.now();
            updatedAt = OffsetDateTime.now();
            bookingDate= OffsetDateTime.now();
            bookingNumber = generateBookingNumber();
        }

        @PreUpdate
        protected void onUpdate() {
            updatedAt = OffsetDateTime.now();
        }

        @PreRemove
        protected void onRemove() {
            deletedAt = OffsetDateTime.now();
        }

        private String generateBookingNumber() {
                String timestamp = String.valueOf(System.currentTimeMillis());
                return "BK" + timestamp.substring(timestamp.length() - 8)+ this.event.getId().toString() + this.user.getId().toString();
        }
}
