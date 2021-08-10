alter table merchant add open tinyint(1) not null;
update merchant set open = false;
