package com.event.event.infrastructure.bookings.dto.calculation;

import com.event.event.entity.Point;
import com.event.event.entity.coupons.Coupon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCalculationDTO {
    private BigDecimal subtotal;
    private Coupon coupon;
    private Point points;
    private BigDecimal tax = new BigDecimal("0.1");

    // Optional fields
    private BigDecimal couponDiscount;
    private BigDecimal pointDiscount;
    private BigDecimal totalTax;
    private BigDecimal finalTotal;
}
