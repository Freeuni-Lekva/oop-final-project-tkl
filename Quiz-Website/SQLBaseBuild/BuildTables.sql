USE final_project;

DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS simple_answers;
DROP TABLE IF EXISTS multiple_choice_answers;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS challenge;
DROP TABLE IF EXISTS quiz_scores;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS friend_requests;
DROP TABLE IF EXISTS friendships;
DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS users(

    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(256) NOT NULL UNIQUE,
    password VARCHAR(512) NOT NULL,
    real_name VARCHAR(256) NOT NULL,
    real_lastname VARCHAR(256) NOT NULL,
    image_path VARCHAR(256),
    description TEXT
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
CREATE TABLE IF NOT EXISTS quizzes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    creator_id BIGINT NOT NULL,
    quiz_name VARCHAR(64) NOT NULL,
    quiz_description VARCHAR(64) NOT NULL,
    creation_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_draft TINYINT NOT NULL,
    is_practice TINYINT NOT NULL,
    is_sorted TINYINT NOT NULL,
    is_one_page TINYINT NOT NULL,
    FOREIGN KEY (creator_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS challenge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    send_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS questions (
    id BIGINT PRIMARY KEY  AUTO_INCREMENT,
    quiz_id BIGINT NOT NULL NULL,
    question_type INT NOT NULL,
    question_text TEXT NOT NULL,
    question_image VARCHAR(64),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS simple_answers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    correct_answer TEXT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS multiple_choice_answers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    question_id BIGINT NOT NULL,
    answer TEXT NOT NULL,
    is_correct TINYINT NOT NULL,
    FOREIGN KEY (question_id) REFERENCES questions(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS quiz_scores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    quiz_id BIGINT,
    score DOUBLE,
    start_time DATETIME,
    end_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS notes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    note TEXT NOT NULL,
    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);












