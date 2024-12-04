-- Create ENUM types
--DO $$
--BEGIN
--    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'event_status') THEN
--        CREATE TYPE event_status AS ENUM (
--            'DRAFT',
--            'PUBLISHED',
--            'CANCELLED',
--            'SOLD_OUT'
--        );
--    END IF;
--END $$;

--CREATE TYPE event_mp.ticket_status AS ENUM ('AVAILABLE', 'RESERVED', 'SOLD', 'CANCELLED');

-- Main events table
CREATE TABLE event_mp.events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    venue VARCHAR(255),
    event_date TIMESTAMP NOT NULL,
    start_time TIME,
    end_time TIME,
    total_tickets INTEGER,
    status VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
--    status event_status DEFAULT 'DRAFT'::event_status,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

-- Separate table for ticket types/tiers if needed
CREATE TABLE event_mp.ticket_types (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    name VARCHAR(100), -- (VIP, Regular, Early Bird etc)
    price DECIMAL(10,2),
    quantity INTEGER,
    description TEXT
);

-- Table for actual tickets
CREATE TABLE event_mp.tickets (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    ticket_type_id BIGINT REFERENCES ticket_types(id),
    user_id BIGINT REFERENCES users(id),
--    status ticket_status DEFAULT 'AVAILABLE',
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    purchase_date TIMESTAMP,
    ticket_code VARCHAR(100) UNIQUE
);