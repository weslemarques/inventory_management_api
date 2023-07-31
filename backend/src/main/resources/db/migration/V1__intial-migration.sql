CREATE TABLE refresh_token (
    id bigint generated by default as identity,
    expiry_date timestamp(6) with time zone not null,
    token varchar(255) not null,
    user_id bigint,
    primary key (id));

CREATE TABLE tb_category (
         id bigint generated by default as identity,
         created_at TIMESTAMP WITHOUT TIME ZONE, name varchar(255),
         updated_at TIMESTAMP WITHOUT TIME ZONE,
         primary key (id));


CREATE TABLE tb_product (
         id bigint generated by default as identity,
         created_at TIMESTAMP WITHOUT TIME ZONE,
         date TIMESTAMP WITHOUT TIME ZONE,
         description TEXT,
         img_url varchar(255),
         name varchar(255),
         price float(53),
         updated_at TIMESTAMP WITHOUT TIME ZONE,
         primary key (id));

CREATE TABLE tb_product_category (
         product_id bigint not null,
         category_id bigint not null,
         primary key (product_id, category_id));


CREATE TABLE tb_role (
         id bigint generated by default as identity,
         authority varchar(255),
         primary key (id));


CREATE TABLE tb_user (
         id bigint generated by default as identity,
         email varchar(255),
         first_name varchar(255),
         last_name varchar(255),
         password varchar(255),
         primary key (id));

CREATE TABLE tb_user_role (
         user_id bigint not null,
         role_id bigint not null,
         primary key (user_id, role_id));

ALTER TABLE if EXISTS refresh_token add constraint UK_r4k4edos30bx9neoq81mdvwph unique (token);
ALTER TABLE if EXISTS refresh_token add constraint FK6bves7e4cicuoyrvwm641o8sk foreign key (user_id) references tb_user;
ALTER TABLE if EXISTS tb_product_category add constraint FK5r4sbavb4nkd9xpl0f095qs2a foreign key (category_id) references tb_category;
ALTER TABLE if EXISTS tb_product_category add constraint FKgbof0jclmaf8wn2alsoexxq3u foreign key (product_id) references tb_product;
ALTER TABLE if EXISTS tb_user_role add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role;
ALTER TABLE if EXISTS tb_user_role add constraint FK7vn3h53d0tqdimm8cp45gc0kl foreign key (user_id) references tb_user;

