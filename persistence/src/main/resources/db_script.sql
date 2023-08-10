# CREATE DATABASE IF NOT EXISTS dbo;

USE dbo;

CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email    VARCHAR(255) UNIQUE,
    password_hash VARCHAR(255)
);

CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


CREATE TABLE classes
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name    varchar(255) not null unique
);

CREATE TABLE grades
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id   BIGINT NOT NULL,
    class_id  BIGINT NOT NULL,
    grade     FLOAT,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE
);

CREATE TABLE user_classes
(
    user_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES classes (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, class_id)
);
