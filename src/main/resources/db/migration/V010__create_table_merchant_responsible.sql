create table merchant_responsible_user (
    merchant_id bigint not null,
    user_id bigint not null,

    primary key (merchant_id, user_id)
) engine=InnoDB default charset=UTF8MB4;

alter table merchant_responsible_user add constraint fk_merchant_user_merchant
    foreign key (merchant_id) references merchant (id);

alter table merchant_responsible_user add constraint fk_merchant_user_user
    foreign key (user_id) references user (id);
