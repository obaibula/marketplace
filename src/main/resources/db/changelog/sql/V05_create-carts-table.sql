-- liquibase formatted sql

-- changeset obaibula:9 contextFilter:prod
CREATE TABLE IF NOT EXISTS carts(
    user_id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    total_price DECIMAL(10, 2) DEFAULT 0
);