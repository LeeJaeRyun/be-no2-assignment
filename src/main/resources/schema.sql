DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS Schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    password VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    created_at DATE,
    updated_at DATE
);