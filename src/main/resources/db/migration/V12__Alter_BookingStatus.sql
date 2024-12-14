ALTER TABLE event_mp.bookings
DROP CONSTRAINT check_booking_status,
ADD CONSTRAINT check_booking_status
CHECK (status IN ('RESERVED', 'BOOKED', 'FAILED', 'PAYMENT_EXPIRED', 'CANCELLED', 'EXPIRED'));