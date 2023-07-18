-- liquibase formatted sql

-- changeset obaibula:1689478830697-1 contextFilter:prod
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


-- changeset obaibula:1689478830697-3 contextFilter:prod
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

-- changeset obaibula:4 contextFilter:test
INSERT INTO "geoip2_network" ("network", "geoname_id", "registered_country_geoname_id", "represented_country_geoname_id", "is_anonymous_proxy", "is_satellite_provider", "postal_code", "latitude", "longitude", "accuracy_radius")
VALUES
    ('176.36.192.0/20', 12345, 54321, NULL, FALSE, FALSE, '12345', 37.7749, -122.4194, 10),
    ('203.0.113.0/24', 54321, 98765, 24680, TRUE, TRUE, '54321', 40.7128, -74.0060, 20),
    ('198.51.100.0/24', NULL, NULL, NULL, FALSE, FALSE, NULL, 34.0522, -118.2437, 15),
    ('2001:db8::/32', 98765, NULL, NULL, TRUE, FALSE, '98765', 51.5074, -0.1278, 30);

-- changeset obaibula:5 contextFilter:test
INSERT INTO "geoip2_location" ("geoname_id", "locale_code", "continent_code", "continent_name", "country_iso_code", "country_name", "subdivision_1_iso_code", "subdivision_1_name", "subdivision_2_iso_code", "subdivision_2_name", "city_name", "metro_code", "time_zone", "is_in_european_union")
VALUES
    (12345, 'en', 'NA', 'North America', 'US', 'United States', 'CA', 'California', NULL, NULL, 'San Francisco', 123, 'America/Los_Angeles', FALSE),
    (54321, 'en', 'AS', 'Asia', 'JP', 'Japan', '13', 'Tokyo', NULL, NULL, 'Tokyo', 456, 'Asia/Tokyo', FALSE),
    (98765, 'en', 'EU', 'Europe', 'GB', 'United Kingdom', 'ENG', 'England', 'LND', 'London', 'London', NULL, 'Europe/London', TRUE),
    (24680, 'en', 'EU', 'Europe', 'DE', 'Germany', 'BB', 'Brandenburg', 'BR', 'Berlin', 'Berlin', 789, 'Europe/Berlin', TRUE);
