-- liquibase formatted sql

-- changeset obaibula:11 contextFilter:prod
CREATE TABLE IF NOT EXISTS favourite_offerings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    offering_id BIGINT REFERENCES offerings(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),

    -- Ensure a user can only have one favorite record for an offering
    CONSTRAINT unique_user_fav_offering UNIQUE (user_id, offering_id)
);
