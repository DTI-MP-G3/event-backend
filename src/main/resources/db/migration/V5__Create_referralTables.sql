CREATE TABLE event_mp.referral_history (
    id BIGSERIAL PRIMARY KEY,
    referrer_id BIGINT NOT NULL,
    referee_id BIGINT NOT NULL,
    reward_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP WITH TIME ZONE,

    -- Constraints
    CONSTRAINT valid_reward_status CHECK (reward_status IN ('PENDING', 'REWARDED', 'EXPIRED')),
    CONSTRAINT fk_referrer FOREIGN KEY (referrer_id) REFERENCES users(id),
    CONSTRAINT fk_referee FOREIGN KEY (referee_id) REFERENCES users(id),
    CONSTRAINT unique_referee UNIQUE (referee_id)
);


ALTER TABLE event_mp.users
ADD COLUMN referral_code VARCHAR(6),
ADD CONSTRAINT unique_referral_code UNIQUE (referral_code);