-- liquibase formatted sql

-- changeset obaibula:1689478830697-2 contextFilter:prod
-- preconditions onFail:MARK_RAN onError:MARK_RAN
-- preconditions-sql-check expectedResult:0 select count(*) from pg_index where tablename = 'geoip2_network_network_idx'
CREATE INDEX IF NOT EXISTS "geoip2_network_network_idx" ON "geoip2_network"("network");
