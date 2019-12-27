create table tb_area(
	area_id int not null auto_increment,
    area_name varchar(200) not null unique,
    priority int not null default 0,
    create_time datetime default null,
    last_edit_time datetime default null,
    primary key (area_id)
);

create table tb_person_info (
	user_id int not null auto_increment primary key,
    name varchar(32) default null,
    profile_img varchar(1024) default null,
    email varchar(1024) default null,
    gender char(1) default null,
    enable_status int not null default 0 comment '0:禁止使用商场，1允许使用',
    user_type int not null default 1 comment '1: 顾客 2: 店家 3: 超级管理员',
    create_time datetime default null,
    last_edit_time datetime default null
);

create table tb_wechat_auth(
	wechat_auth_id int not null auto_increment primary key,
    user_id int not null,
    open_id varchar(256) not null,
    create_time datetime default null,
    last_edit_time datetime default null,
    foreign key(user_id) references tb_person_info(user_id)
);

create table tb_local_auth(
	local_auth_id int not null auto_increment primary key,
    user_id int not null,
    user_name varchar(128) not null,
    password varchar(128) not null,
    create_time datetime default null,
    last_edit_time datetime default null,
    unique key local_profile(user_name),
    foreign key(user_id) references tb_person_info(user_id)
);

alter table tb_wechat_auth add unique index(open_id);

create table tb_head_line(
	line_id int not null auto_increment primary key,
    line_name varchar(1000) default null,
    line_link varchar(2000) not null,
    link_img varchar(2000) not null,
    priority int default null,
    enable_status int not null default 0,
    create_time datetime default null,
    last_edit_time datetime default null
);

create table tb_shop_category(
	shop_category_id int not null auto_increment primary key,
    shop_category_name varchar(100) not null default '',
    shop_category_desc varchar(1000) default '',
    shop_category_img varchar(2000) default null,
    priority int not null default 0,
    create_time datetime default null,
    last_edit_time datetime default null,
    parent_id int default null,
    foreign key (parent_id) references tb_shop_category(shop_category_id)
);

create table tb_shop(
	shop_id int not null auto_increment primary key,
    owner_id int not null comment "店铺创始人",
    area_id int default null,
    shop_category_id int default null,
    shop_name varchar(256) not null,
    shop_desc varchar(1024) default null,
    shop_addr varchar(200) default null,
    phone varchar(128) default null,
    shop_img varchar(1024) default null,
    priority int default 0,
    create_time datetime default null,
    last_edit_time datetime default null,
    enable_status int not null default 0,
    advice varchar(255) default null,
    foreign key (owner_id) references tb_person_info(user_id),
    foreign key (area_id) references tb_area(area_id),
    foreign key (shop_category_id) references tb_shop_category(shop_category_id)
);

create table tb_product_category(
	product_category_id int not null auto_increment primary key,
    product_category_name varchar(100) not null,
    priority int default 0,
    create_time datetime default null,
    shop_id int not null default 0,
    foreign key (shop_id) references tb_shop(shop_id)
);

create table tb_product(
	product_id int not null auto_increment primary key,
    product_name varchar(100) not null,
    product_desc varchar(2000) default null,
    img_addr varchar(2000) default '',
    normal_price varchar(100) default null,
    promotion_price varchar(100) default null,
    priority int not null default 0,
    create_time datetime default null,
    last_edit_time datetime default null,
    enable_status int not null default 0,
    product_category_id int default null,
    shop_id int not null default 0,
    foreign key (product_category_id) references tb_product_category(product_category_id),
    foreign key (shop_id) references tb_shop(shop_id)
);

create table tb_product_img(
	product_img_id int not null auto_increment primary key,
    img_addr varchar(2000) not null,
    img_desc varchar(2000) default null,
    priority int default 0,
    create_time datetime default null,
    product_id int default null,
    foreign key (product_id) references tb_product(product_id)
);