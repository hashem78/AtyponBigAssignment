CREATE DATABASE IF NOT EXISTS dbo;

USE dbo;

CREATE TABLE Users
(
    id       INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email    VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

CREATE TABLE Roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)
);

CREATE TABLE UserRoles
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);


CREATE TABLE Classes
(
    id      INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name    varchar(100) not null unique
);

CREATE TABLE Grades
(
    id        INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id   INT NOT NULL,
    class_id  INT NOT NULL,
    grade     FLOAT,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES Classes (id) ON DELETE CASCADE
);

CREATE TABLE UserClasses
(
    user_id INT NOT NULL,
    class_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE,
    FOREIGN KEY (class_id) REFERENCES Classes (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, class_id)
);
