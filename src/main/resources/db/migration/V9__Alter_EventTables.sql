
ALTER TABLE event_mp.events
ADD COLUMN user_organizer_id BIGINT,
ADD CONSTRAINT fk_user_user_organizer_id
Foreign key (user_organizer_id)
REFERENCES event_mp.users(id);

