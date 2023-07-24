-- liquibase formatted sql

-- changeset obaibula:10 contextFilter:prod
CREATE TABLE offerings(
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
    category TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
);