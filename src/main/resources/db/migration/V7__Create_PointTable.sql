CREATE TABLE event_mp.points_users (
    id BIGSERIAL PRIMARY KEY ,
    user_id BIGINT NOT NULL,
    points_amount INTEGER NOT NULL,
    point_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE,

    -- Constraints
    CONSTRAINT valid_point_status CHECK (point_status IN ('ACTIVE', 'USED', 'EXPIRED')),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id)

);