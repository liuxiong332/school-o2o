CREATE TABLE user(
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(128),
    password VARCHAR(128) NOT NULL,
    age INT,
    email VARCHAR(128),
    create_time DATETIME,
    update_time DATETIME
);