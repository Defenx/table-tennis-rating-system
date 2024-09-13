--liquibase formatted sql

--changeset VladislavDuninHero:4
ALTER TABLE matches
ADD tournament_stage INTEGER;