-- liquibase formatted sql

--changeset re1kur:1
ALTER TABLE users
ADD COLUMN is_verified BOOLEAN DEFAULT false;