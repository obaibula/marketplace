-- liquibase formatted sql

-- changeset obaibula:6 contextFilter:prod
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'CUSTOMER'
)

-- changeset obaibula:7 contextFilter:test
-- Create test data for users table
INSERT INTO users(email, password)
VALUES('user@mail.com', 'qWaSzX123!#')