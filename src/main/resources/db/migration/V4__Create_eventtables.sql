-- Create ENUM types
CREATE TYPE event_status AS ENUM ('DRAFT', 'PUBLISHED', 'CANCELLED', 'SOLD_OUT');
CREATE TYPE ticket_status AS ENUM ('AVAILABLE', 'RESERVED', 'SOLD', 'CANCELLED');

-- Main events table
CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    venue VARCHAR(255),
    event_date TIMESTAMP NOT NULL,
    start_time TIME,
    end_time TIME,
    total_tickets INTEGER,
    status event_status DEFAULT 'DRAFT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Separate table for ticket types/tiers if needed
CREATE TABLE ticket_types (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    name VARCHAR(100), -- (VIP, Regular, Early Bird etc)
    price DECIMAL(10,2),
    quantity INTEGER,
    description TEXT
);

-- Table for actual tickets
CREATE TABLE tickets (
    id BIGSERIAL PRIMARY KEY,
    event_id BIGINT REFERENCES events(id),
    ticket_type_id BIGINT REFERENCES ticket_types(id),
    user_id BIGINT REFERENCES users(id),
    status ticket_status DEFAULT 'AVAILABLE',
    purchase_date TIMESTAMP,
    ticket_code VARCHAR(100) UNIQUE
);