package com.event.event.infrastructure.bookings.dto;


import com.event.event.entity.booking.Booking;
import com.event.event.entity.booking.BookingDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingResponseDTO {
    private Long id;
    private String bookingNumber;
    private Long paymentId;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal total;
    private OffsetDateTime bookingDate;
    private String eventName;
    private List<BookingDetailResponseDTO> bookingDetails;




    public CreateBookingResponseDTO toResponseDTO(Booking booking,Long paymentId) {
        return CreateBookingResponseDTO.builder()
                .id(booking.getId())
                .bookingNumber(booking.getBookingNumber())
                .status(booking.getStatus().toString())
                .paymentId(paymentId)
                .subtotal(booking.getSubtotal())
                .discount(booking.getDiscountAmount())
                .tax(booking.getTaxAmount())
                .total(booking.getTotalAmount())
                .bookingDate(booking.getCreatedAt())
                .eventName(booking.getEvent().getName())
                .bookingDetails(toBookingDetailResponseDTOs(booking.getBookingDetails()))
                .build();
    }

    private List<BookingDetailResponseDTO> toBookingDetailResponseDTOs(List<BookingDetail> details) {
        return details.stream()
                .map(detail -> BookingDetailResponseDTO.builder()
                        .ticketTypeName(detail.getTicketType().getName())
                        .quantity(detail.getQuantity())
                        .unitPrice(detail.getUnitPrice())
                        .subtotal(detail.getSubtotal())
                        .build())
                .collect(Collectors.toList());
    }
}
