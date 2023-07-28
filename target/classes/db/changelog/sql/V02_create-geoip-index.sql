-- liquibase formatted sql

-- changeset obaibula:1689478830697-2 contextFilter:prod
-- Create index to geoip2_network table
CREATE INDEX IF NOT EXISTS "geoip2_network_network_idx" ON "geoip2_network"("network");
