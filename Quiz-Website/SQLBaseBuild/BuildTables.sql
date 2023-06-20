USE final_project;

DROP TABLE IF EXISTS users;

CREATE TABLE users(

    id              BIGINT          PRIMARY KEY,
    name            VARCHAR(256)    NOT NULL UNIQUE,
    password        VARCHAR(512)    NOT NULL,
    real_name       VARCHAR(256)    NOT NULL,
    real_lastname   VARCHAR(256)    NOT NULL,
    image_path      VARCHAR(256),
    description     TEXT
);