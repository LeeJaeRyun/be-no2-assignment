DROP TABLE IF EXISTS Schedule;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at DATE,
    updated_at DATE
);

CREATE TABLE IF NOT EXISTS Schedule (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT NOT NULL,
    password VARCHAR(50) NOT NULL,
    user_id BIGINT,
    created_at DATE,
    updated_at DATE,
    CONSTRAINT fk_schedule_user FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE SET NULL
);
