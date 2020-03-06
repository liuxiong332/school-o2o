
CREATE TABLE delivery (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    commodity_id BIGINT NOT NULL,
    seckill_id BIGINT,
    quantity INT NOT NULL,
    price FLOAT NOT NULL,
    create_time DATETIME,
    update_time DATETIME,
    FOREIGN KEY (commodity_id) REFERENCES commodity(id),
    FOREIGN KEY (seckill_id) REFERENCES seckill(id)
);