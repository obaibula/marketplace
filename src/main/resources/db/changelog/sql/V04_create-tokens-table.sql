-- liquibase formatted sql

-- changeset obaibula:6 contextFilter:prod
CREATE TABLE IF NOT EXISTS tokens(
    id BIGSERIAL PRIMARY KEY,
    token TEXT,
    token_type TEXT,
    expired BOOLEAN,
    revoked BOOLEAN,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE
)