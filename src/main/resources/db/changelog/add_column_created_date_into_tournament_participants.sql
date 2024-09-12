--liquibase formatted sql

--changeset k0ct0pka:3
ALTER TABLE tournament_participants ADD COLUMN created_date TIMESTAMP;
COMMENT ON COLUMN tournament_participants.created_date IS 'Дата создания турнира'
