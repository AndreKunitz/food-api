create table group$ (
    id   bigint       not null auto_increment,
    name varchar(60) not null,

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;

create table permission$ (
    id          bigint       not null auto_increment,
    description varchar(60) not null,
    name        varchar(100) not null,

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;;

create table merchant (
    id                   bigint         not null auto_increment,
    cuisine_id           bigint         not null,
    name                 varchar(80)    not null,
    delivery_fee         decimal(10, 2) not null,
    registration_date    datetime       not null,
    update_date          datetime       not null,

    address_city_id      bigint,
    address_zip_code     varchar(9),
    address_street       varchar(100),
    address_number       varchar(20),
    address_neighborhood varchar(60),

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;

create table merchant_payment_method (
    merchant_id       bigint not null,
    payment_method_id bigint not null,

    primary key (merchant_id, payment_method_id)
) engine = InnoDB default charset = UTF8MB4;

create table payment_method (
    id          bigint       not null auto_increment,
    description varchar(60) not null,

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;

create table permission_group (
    group_id      bigint not null,
    permission_id bigint not null,

    primary key (group_id, permission_id)
) engine = InnoDB default charset = UTF8MB4;

create table product (
    id          bigint not null auto_increment,
    merchant_id bigint not null,
    name        varchar(80) not null,
    description text not null,
    price       decimal(10, 2) not null,
    active      tinyint(1) not null,

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;

create table user (
    id                bigint       not null auto_increment,
    name              varchar(80) not null,
    email             varchar(255) not null,
    password          varchar(255) not null,
    registration_date datetime     not null,

    primary key (id)
) engine = InnoDB default charset = UTF8MB4;

create table user_group (
    user_id  bigint not null,
    group_id bigint not null,

    primary key (user_id, group_id)
) engine = InnoDB default charset = UTF8MB4;

alter table merchant
    add constraint fk_merchant_city foreign key (address_city_id) references city (id);

alter table merchant
    add constraint fk_merchant_cuisine foreign key (cuisine_id) references cuisine (id);

alter table merchant_payment_method
    add constraint fk_merchant_pay_method_pay_method
        foreign key (payment_method_id) references payment_method (id);

alter table merchant_payment_method
    add constraint fk_merchant_payment_method_merchant
        foreign key (merchant_id) references merchant (id);

alter table permission_group
    add constraint fk_permission_group_permission
        foreign key (permission_id) references permission$ (id);

alter table permission_group
    add constraint fk_permission_group_group foreign key (group_id) references group$ (id);

alter table product
    add constraint fk_merchant_product foreign key (merchant_id) references merchant (id);

alter table user_group
    add constraint fk_user_group_group foreign key (group_id) references group$ (id);

alter table user_group
    add constraint fk_user_group_user foreign key (user_id) references user (id);