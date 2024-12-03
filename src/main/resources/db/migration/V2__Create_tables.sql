
CREATE TABLE event_mp.users (
    id bigserial  constraint users_pk  PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(100),
    email VARCHAR(150),
    created_at timestamp with time zone default CURRENT_TIMESTAMP not null,
    updated_at timestamp with time zone default CURRENT_TIMESTAMP not null,
    deleted_at timestamp with time zone
);


--CREATE TABLE event_mp.pocket (
--    id SERIAL PRIMARY KEY,
--    wallet_id INTEGER REFERENCES event_mp.wallet(id),
--    name VARCHAR(50),
--    emoji_data VARCHAR(10),
--    description VARCHAR(200),
--    budget_amount BIGINT DEFAULT 0,
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    deleted_at TIMESTAMP
--);