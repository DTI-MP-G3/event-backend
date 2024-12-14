package com.event.event.infrastructure.bookings.repository;

import com.event.event.entity.booking.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetail,Long> {
}
