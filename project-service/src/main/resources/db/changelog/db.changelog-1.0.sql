--liquibase formatted sql

--changeset re1kur:1
CREATE TABLE IF NOT EXISTS projects
(
    id SERIAL PRIMARY KEY,
    title VARCHAR(32) NOT NULL UNIQUE,
    description TEXT
);

--changeset re1kur:2
CREATE TABLE IF NOT EXISTS projects_tasks(
    project_id INTEGER,
    task_id BIGINT,
    PRIMARY KEY (project_id, task_id),
    FOREIGN KEY(project_id) REFERENCES projects(id) ON DELETE CASCADE
);