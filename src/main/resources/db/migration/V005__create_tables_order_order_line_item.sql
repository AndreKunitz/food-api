create table `order` (
    id                     bigint         not null auto_increment,
    subtotal               decimal(10, 2) not null,
    delivery_fee           decimal(10, 2) not null,
    total_price            decimal(10, 2) not null,

    merchant_id            bigint         not null,
    user_client_id         bigint         not null,
    payment_method_id      bigint         not null,

    address_city_id        bigint(20)     not null,
    address_zip_code       varchar(9)     not null,
    address_street         varchar(100)   not null,
    address_number         varchar(20)    not null,
    address_neighborhood   varchar(60)    not null,

    status                 varchar(10)    not null,
    registration_timestamp datetime       not null,
    confirmation_timestamp datetime       null,
    cancellation_timestamp datetime       null,
    delivered_timestamp    datetime       null,

    primary key (id),

    constraint fk_order_merchant foreign key (merchant_id) references merchant (id),
    constraint fk_order_user_client foreign key (user_client_id) references user (id),
    constraint fk_order_payment_method foreign key (payment_method_id) references payment_method (id)
) engine = InnoDB
  default charset = UTF8MB4;

create table order_line_item (
    id          bigint         not null auto_increment,
    quantity    smallint(6)    not null,
    unit_price  decimal(10, 2) not null,
    total_price decimal(10, 2) not null,
    observation varchar(255)   null,
    order_id    bigint         not null,
    product_id  bigint         not null,

    primary key (id),
    unique key uk_order_line_item_order_product (order_id, product_id),

    constraint fk_order_line_item_order foreign key (order_id) references `order` (id),
    constraint fk_order_line_item_product foreign key (product_id) references product (id)
) engine = InnoDB
  default charset = UTF8MB4;