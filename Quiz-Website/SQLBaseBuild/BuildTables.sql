USE final_project;

DROP TABLE IF EXISTS friend_requests;
DROP TABLE IF EXISTS friendships;
DROP TABLE IF EXISTS users;


CREATE TABLE users(

    id              BIGINT          PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(256)    NOT NULL UNIQUE,
    password        VARCHAR(512)    NOT NULL,
    real_name       VARCHAR(256)    NOT NULL,
    real_lastname   VARCHAR(256)    NOT NULL,
    image_path      VARCHAR(256),
    description     TEXT
);

CREATE TABLE IF NOT EXISTS friend_requests (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id   BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    send_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendships (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_user_id  BIGINT NOT NULL,
    second_user_id BIGINT NOT NULL,
    FOREIGN KEY (first_user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (second_user_id) REFERENCES users(id) ON DELETE CASCADE
);










