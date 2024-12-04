
CREATE TABLE event_mp.users (
    id bigserial  constraint users_pk  PRIMARY KEY,
    password VARCHAR(255),
    name VARCHAR(100),
    email varchar(50) not null constraint users_email_unique unique,
    created_at timestamp with time zone default CURRENT_TIMESTAMP not null,
    updated_at timestamp with time zone default CURRENT_TIMESTAMP not null,
    deleted_at timestamp with time zone
);


