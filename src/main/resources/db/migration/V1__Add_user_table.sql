create table user(
    user_id int not null,
    user_name varchar(128) not null,
    password varchar(128) not null,
    create_time datetime default null,
    update_time datetime default null
);