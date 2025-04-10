--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(256) NOT NULL UNIQUE,
    password    VARCHAR(128),
    is_verified BOOLEAN      NOT NULL DEFAULT FALSE,
    is_oauth    BOOLEAN      NOT NULL DEFAULT FALSE
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS roles
(
    id   SMALLSERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);

--changeset re1kur:3
CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT,
    role_id SMALLINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);