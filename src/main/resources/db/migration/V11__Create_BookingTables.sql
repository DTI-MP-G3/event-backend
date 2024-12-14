CREATE TABLE bookings (
   id BIGSERIAL PRIMARY KEY,
   user_id BIGINT references users(id),
   event_id BIGINT REFERENCES events(id),
   booking_date TIMESTAMP WITH TIME ZONE NOT NULL,
   status VARCHAR(20),
   subtotal DECIMAL(10,2),
   booking_number varchar(20) Not NULL,
   coupon_id BIGINT REFERENCES coupons(id),
   point_id BIGINT REFERENCES points_users(id),
   fee_amount DECIMAL(10,2),
   tax_amount DECIMAL(10,2),
   discount_amount DECIMAL(10,2),
   point_amount DECIMAL(10,2),
   total_amount DECIMAL(10,2),
   created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
   deleted_at TIMESTAMP WITH TIME ZONE,
   constraint check_booking_status CHECK (status IN ('RESERVE', 'BOOKED', 'FAILED','PAYMENT_EXPIRED', 'CANCELLED', 'EXPIRED'))
);


CREATE TABLE booking_details (
    id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT references bookings(id),
    ticket_type_id BIGINT references ticket_types(id),
    quantity INTEGER,
    unit_price DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE payment (
    id BIGSERIAL PRIMARY KEY,
    booking_id BIGINT references bookings(id),
    amount DECIMAL(10,2) NOT NULL,
    PAYMENT_METHOd varchar(50),
    STATUS Varchar(20),
    payment_date TIMESTAMP with TIME ZONE,
    reference_id VARCHAR(30),
    timeout_at TIMESTAMP WITH TIME ZONE ,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE,
    constraint check_payment_status CHECK (status IN ('PAID', 'PENDING', 'FAILED', 'EXPIRED'))
);



ALTER TABLE event_mp.events
ALTER COLUMN event_date TYPE TIMESTAMP WITH TIME ZONE,
ALTER COLUMN start_time TYPE TIME WITH TIME ZONE,
ALTER COLUMN end_time TYPE TIME WITH TIME ZONE,
ALTER COLUMN created_at TYPE TIMESTAMP WITH TIME ZONE,
ALTER COLUMN updated_at TYPE TIMESTAMP WITH TIME ZONE,
ALTER COLUMN deleted_at TYPE TIMESTAMP WITH TIME ZONE;


ALTER TABLE event_mp.coupons
ADD COLUMN coupon_type VARCHAR(20),
ADD CONSTRAINT check_coupon_type CHECK (coupon_type in ('EVENT', 'GENERAL', 'USER'));