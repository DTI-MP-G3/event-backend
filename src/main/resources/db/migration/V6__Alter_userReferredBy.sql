ALTER TABLE event_mp.users
ADD COLUMN referred_by BIGINT,
ADD CONSTRAINT fk_user_referral
    FOREIGN KEY (referred_by)
    REFERENCES event_mp.users(id);