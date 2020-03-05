CREATE TABLE commodity(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(128) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    price FLOAT NOT NULL,
    create_time DATETIME,
    update_time DATETIME,
    INDEX(name)
);

CREATE TABLE inventory(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    commodity_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (commodity_id) REFERENCES commodity(id)
);

CREATE TABLE seckill (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    commodity_id BIGINT NOT NULL,
    seckill_price FLOAT NOT NULL,
    from_time DATETIME NOT NULL,
    to_time DATETIME NOT NULL,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (commodity_id) REFERENCES commodity(id)
);