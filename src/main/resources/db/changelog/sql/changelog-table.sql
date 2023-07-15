-- liquibase formatted sql

-- changeset obaibula:1689478830697-1
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- preconditions-sql-check expectedResult:0 select count(*) from pg_tables where tablename = 'geoip2_network'
CREATE TABLE IF NOT EXISTS "geoip2_network" (
    "network" CIDR NOT NULL,
    "geoname_id" INTEGER,
    "registered_country_geoname_id" INTEGER,
    "represented_country_geoname_id" INTEGER,
    "is_anonymous_proxy" BOOLEAN,
    "is_satellite_provider" BOOLEAN,
    "postal_code" TEXT,
    "latitude" numeric,
    "longitude" numeric,
    "accuracy_radius" INTEGER);


-- changeset obaibula:1689478830697-3
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- preconditions-sql-check expectedResult:0 select count(*) from pg_tables where tablename = 'geoip2_location'
CREATE TABLE IF NOT EXISTS "geoip2_location" (
    "geoname_id" INTEGER NOT NULL,
    "locale_code" TEXT NOT NULL,
    "continent_code" TEXT NOT NULL,
    "continent_name" TEXT NOT NULL,
    "country_iso_code" TEXT,
    "country_name" TEXT,
    "subdivision_1_iso_code" TEXT,
    "subdivision_1_name" TEXT,
    "subdivision_2_iso_code" TEXT,
    "subdivision_2_name" TEXT,
    "city_name" TEXT,
    "metro_code" INTEGER,
    "time_zone" TEXT,
    "is_in_european_union"
    BOOLEAN NOT NULL,
    CONSTRAINT "geoip2_location_pkey" PRIMARY KEY ("geoname_id", "locale_code"));

