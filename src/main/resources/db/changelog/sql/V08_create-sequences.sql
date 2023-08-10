-- liquibase formatted sql

-- changeset obaibula:12 contextFilter:prod
CREATE SEQUENCE IF NOT EXISTS favourite_offerings_seq START 200 INCREMENT 50;

-- changeset obaibula:13 contextFilter:prod
CREATE SEQUENCE IF NOT EXISTS users_seq START 200 INCREMENT 50;

-- changeset obaibula:14 contextFilter:prod
CREATE SEQUENCE IF NOT EXISTS tokens_seq START 200 INCREMENT 50;

-- changeset obaibula:15 contextFilter:prod
CREATE SEQUENCE IF NOT EXISTS offerings_seq START 200 INCREMENT 50;