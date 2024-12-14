package com.event.event.usecase.booking.impl;

import com.event.event.common.exception.DataNotFoundException;
import com.event.event.common.exception.InsufficientTicketsException;

import com.event.event.entity.Event;
import com.event.event.entity.Point;

import com.event.event.entity.Ticket.TicketType;
import com.event.event.entity.User;
import com.event.event.entity.booking.Booking;
import com.event.event.entity.booking.BookingDetail;
import com.event.event.entity.coupons.Coupon;
import com.event.event.entity.coupons.EventCoupon;
import com.event.event.entity.coupons.UserCoupon;
import com.event.event.enums.BookingStatus;
import com.event.event.enums.CouponType;
import com.event.event.enums.DiscountType;
import com.event.event.infrastructure.bookings.dto.BookingDetailRequestDTO;
import com.event.event.infrastructure.bookings.dto.BookingRequestDTO;
import com.event.event.infrastructure.bookings.dto.BookingResponseDTO;
import com.event.event.infrastructure.bookings.dto.calculation.BookingCalculationDTO;
import com.event.event.infrastructure.bookings.repository.BookingRepository;

import com.event.event.usecase.booking.CreateBookingUsecase;
import com.event.event.usecase.coupon.SearchCouponUsecase;
import com.event.event.usecase.event.SearchEventUseCase;
import com.event.event.usecase.point.ReferralPointUsecase;
import com.event.event.usecase.ticketType.SearchTicketTypeUsecase;
import com.event.event.usecase.ticketType.TicketTypeUsecase;
import com.event.event.usecase.user.SearchUserUsecase;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
public class CreateBookingUsecaseImpl implements CreateBookingUsecase {

    private final BookingRepository bookingRepository;
    private final SearchUserUsecase searchUserUsecase;

    private final SearchEventUseCase searchEventUseCase;

    private final SearchCouponUsecase searchCouponUsecase;

    private final ReferralPointUsecase referralPointUsecase;
    private final SearchTicketTypeUsecase searchTicketTypeUsecase;
    private final TicketTypeUsecase ticketTypeUsecase;

    public CreateBookingUsecaseImpl(BookingRepository bookingRepository,
                                    SearchUserUsecase searchUserUsecase,
                                    SearchEventUseCase searchEventUseCase,
                                    SearchCouponUsecase searchCouponUsecase,
                                    ReferralPointUsecase referralPointUsecase,
                                    SearchTicketTypeUsecase searchTicketTypeUsecase,
                                    TicketTypeUsecase ticketTypeUsecase) {
        this.bookingRepository = bookingRepository;
        this.searchUserUsecase = searchUserUsecase;
        this.searchEventUseCase = searchEventUseCase;
        this.searchCouponUsecase = searchCouponUsecase;
        this.referralPointUsecase = referralPointUsecase;
        this.searchTicketTypeUsecase = searchTicketTypeUsecase;
        this.ticketTypeUsecase =ticketTypeUsecase;
    }

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO dto, Long userId  ){
         Integer eventId = dto.getEventId();
         Long couponId = dto.getCouponId();
         Long pointId = dto.getPointId();
        Booking newBooking = new Booking();

         if(couponId != null){
             Coupon coupon = searchCouponUsecase.findById(couponId).orElseThrow(()->new DataNotFoundException("Coupon not Found"));
             if (coupon.getCouponType() == CouponType.USER){
                 UserCoupon userCoupon = searchCouponUsecase.findUserCouponByIdAndUserId(couponId,userId).orElseThrow(()->new DataNotFoundException("Coupon for this User not Found"));
             }
             if(coupon.getCouponType() == CouponType.EVENT){
                 EventCoupon eventCoupon = searchCouponUsecase.findEventCouponByCouponIdAndEventId(couponId,eventId).orElseThrow(()->new DataNotFoundException("Coupon for this Event not Found"));
             }
             newBooking.setCoupon(coupon);
         }

        if(pointId != null){
            Point point = referralPointUsecase.getPointByIdAndUserId(userId,pointId).orElseThrow(()->new DataNotFoundException("Point not Found"));
            newBooking.setPoint(point);
        }

        List<BookingDetailRequestDTO>  bookingDetailsDTO= dto.getBookingDetails();

        Event event = searchEventUseCase.findEventById(eventId)
                .filter(Objects::nonNull)
                .orElseThrow(() -> {
                    log.info("BookingService: EventId not found with id : {} ", eventId);
                    return new DataNotFoundException("Event not found");
                });
        User user = searchUserUsecase.findUserbyId(userId)
                .filter(Objects::nonNull)
                .orElseThrow(() -> {
                    log.info("BookingService: EventId not found with id : {} ", userId);
                    return new DataNotFoundException("user not found");
                });
        Set<Long> ticketTypeIds = bookingDetailsDTO.stream()
                .map(BookingDetailRequestDTO::getTicketTypeId)
                .collect(Collectors.toSet());

        List<TicketType> ticketTypes = searchTicketTypeUsecase.findAllTicketTypesById(ticketTypeIds);

        if(ticketTypes.size() != ticketTypeIds.size()){
            throw new DataNotFoundException("ticket type not valid");
        }


        Map<Long, TicketType> ticketTypeMap = ticketTypes.stream().collect(Collectors.toMap(TicketType::getId, type->type));
        validateTicketTypeAvailability(bookingDetailsDTO, ticketTypeMap);


        newBooking.setSubtotal(BigDecimal.valueOf(0));
        List<BookingDetail> newBookingDetails= new ArrayList<>();

//        make every bookingDetail and then set to Booking then save from booking
        for(BookingDetailRequestDTO bookDtos: bookingDetailsDTO){
            log.info("Set booking for this booking detail {}, quantity {}", bookDtos.getTicketTypeId(),bookDtos.getQuantity());
            BookingDetail newBookingDetail = new BookingDetail();
            TicketType newTicketType = ticketTypeMap.get(bookDtos.getTicketTypeId());
            newBookingDetail.setTicketType(newTicketType);
            newBookingDetail.setUnitPrice(newTicketType.getPrice());
            newBookingDetail.setBooking(newBooking);
            newBookingDetail.setQuantity(bookDtos.getQuantity());
            newBookingDetail.methodSetSubtotal();
            ticketTypeUsecase.decreaseTicketAvailability(newTicketType,bookDtos.getQuantity());
            newBooking.setSubtotal(newBooking.getSubtotal().add(newBookingDetail.getSubtotal()));
            newBookingDetails.add(newBookingDetail);
        }

        newBooking.setEvent(event);
        newBooking.setUser(user);
        newBooking.setBookingDetails(newBookingDetails);
        newBooking.setStatus(BookingStatus.RESERVED);


        BookingCalculationDTO calculation = new BookingCalculationDTO();
        calculation.setSubtotal(newBooking.getSubtotal());
        if (couponId != null) {
            calculation.setCoupon(newBooking.getCoupon());
        }
        if (pointId != null) {
            calculation.setPoints(newBooking.getPoint());
        }

        BookingCalculationDTO finalCalculation = calculateTotal(calculation);

        newBooking.setDiscountAmount(finalCalculation.getCouponDiscount());
        newBooking.setPointAmount(finalCalculation.getPointDiscount());
        newBooking.setTaxAmount(finalCalculation.getTotalTax());
        newBooking.setTotalAmount(finalCalculation.getFinalTotal());

        log.info("Saving Booking....");
        Booking savedBooking = bookingRepository.save(newBooking);
        BookingResponseDTO response = new BookingResponseDTO();
        return response.toResponseDTO(savedBooking);

    }






    private void validateTicketTypeAvailability(List<BookingDetailRequestDTO> detailsDTO, Map<Long, TicketType> ticketTypeMap) {
        for (BookingDetailRequestDTO detail : detailsDTO) {
            TicketType ticketType = ticketTypeMap.get(detail.getTicketTypeId());

            if (ticketType == null) {
                throw new DataNotFoundException("CreateBookingService: Ticket type not found: " + detail.getTicketTypeId());
            }

            if (ticketType.getQuantityAvailable() < detail.getQuantity()) {
                throw new InsufficientTicketsException(
                        String.format("CreateBookingService :Insufficient tickets available for type %s. Requested: %d, Available: %d",
                                ticketType.getName(),
                                detail.getQuantity(),
                                ticketType.getQuantityAvailable())
                );
            }
        }
    }


    private BookingCalculationDTO calculateTotal(BookingCalculationDTO calc) {
        // Calculate coupon discount
        log.info("Calculating Total ...");
        Coupon coupon = calc.getCoupon();
        Point point = calc.getPoints();
        BigDecimal subtotal = calc.getSubtotal();
        BigDecimal couponDiscount  = new BigDecimal(0);
        BigDecimal pointDiscount = new BigDecimal(0);
        if(calc.getCoupon() != null){
            DiscountType discountType = coupon.getDiscountType();

            if (discountType == DiscountType.FIXED){
                couponDiscount= coupon.getDiscountValue();
                calc.setCouponDiscount(couponDiscount);

            } else if (discountType == DiscountType.PERCENTAGE) {
                couponDiscount= subtotal.multiply(coupon.getDiscountValue());
                calc.setCouponDiscount(couponDiscount);
            }

        }

        if (point != null){
            pointDiscount = new BigDecimal(point.getPoints());
            calc.setPointDiscount(pointDiscount);
        }


        BigDecimal totalTax = calc.getTax().multiply(subtotal);
        calc.setTotalTax(totalTax);

        BigDecimal finalTotal = calc.getSubtotal().add(totalTax).subtract(couponDiscount).subtract(pointDiscount);
        calc.setFinalTotal(finalTotal);
        return calc;
    }
}
