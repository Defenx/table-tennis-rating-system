--liquibase formatted sql

--changeset Borisford:2
ALTER TABLE users
ALTER COLUMN rating SET DATA TYPE decimal(10, 0);