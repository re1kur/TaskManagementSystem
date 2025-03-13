--liquibase formatted sql

--changeset re1kur:1
create table if not exists project
(
    id   serial primary key,
    name varchar(128) not null unique
);

--changeset re1kur:2
create table if not exists task
(
    id          serial primary key,
    project_id  int         not null,
    name        varchar(64) not null default 'unnamed',
    description text,
    start_date  date        not null default now(),
    dead_date   date,
    status      varchar(12) not null default 'TO DO',
    foreign key (project_id) references project (id) on delete cascade
);

--changeset re1kur:3
create table if not exists users
(
    id       serial primary key,
    username varchar(64)  not null,
    email    varchar(256) not null unique,
    password varchar(128),
    role     varchar(32)  not null default 'USER',
    is_oauth boolean      not null default false
);

--changeset re1kur:4
create table if not exists project_users
(
    user_id    int not null,
    project_id int not null,
    primary key (user_id, project_id),
    foreign key (user_id) references users (id) on delete cascade,
    foreign key (project_id) references project (id) on delete cascade
);