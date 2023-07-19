-- liquibase formatted sql

-- changeset obaibula:6 contextFilter:prod
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'CUSTOMER'
)