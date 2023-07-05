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



DROP TABLE IF EXISTS messages;

CREATE TABLE messages(

    id_1            BIGINT          NOT NULL,
    id_2            BIGINT          NOT NULL,
    msg_text        TEXT            NOT NULL
);


DROP TABLE IF EXISTS quizzes;

CREATE TABLE quizzes(

    id              BIGINT          PRIMARY KEY,
    creator_id      BIGINT          NOT NULL,
    question_id     BIGINT          NOT NULL UNIQUE,
    description     TEXT,
    practice_mode   BOOLEAN         NOT NULL,
    participants    BIGINT          DEFAULT 0,
    create_time     DATETIME        NOT NULL,
    submit_type     BOOLEAN         NOT NULL
);


DROP TABLE IF EXISTS questions;

CREATE TABLE questions(

    id              BIGINT          NOT NULL,
    type            VARCHAR(256)    NOT NULL,
    question        TEXT            NOT NULL,
    img_path        VARCHAR(256),
    answer          TEXT            NOT NULL,
    choice_1        TEXT,
    choice_2        TEXT,
    choice_3        TEXT,
    choice_4        TEXT

);

DROP TABLE IF EXISTS active_quizzes;

CREATE TABLE active_quizzes(

    user_id             BIGINT          NOT NULL,
    quiz_id             BIGINT          NOT NULL,
    start_time          DATETIME        NOT NULL,
    answered_questions  BIGINT          DEFAULT 0,
    answers             TEXT

);


DROP TABLE IF EXISTS histories;

CREATE TABLE histories(
    user_id             BIGINT          NOT NULL,
    quiz_id             BIGINT          NOT NULL,
#   1/0 ეს რისია
    time                DATETIME        NOT NULL,
    result              BIGINT          NOT NULL
);



DROP TABLE IF EXISTS invites;

CREATE TABLE invites(
    from_id             BIGINT          NOT NULL,
    to_id               BIGINT          NOT NULL,
    quiz_id             BIGINT          NOT NULL
);









