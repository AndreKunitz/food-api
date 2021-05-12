INSERT INTO CUISINE(id, `name`) VALUES (1, 'Thai');
INSERT INTO CUISINE(id, `name`) VALUES (2, 'Indian');
INSERT INTO CUISINE(id, `name`) VALUES (3, 'Italian');

INSERT INTO MERCHANT (`name`, delivery_fee, cuisine_id) VALUES ('Thai Gourmet', 10, 1);
INSERT INTO MERCHANT (`name`, delivery_fee, cuisine_id) VALUES ('Thai Delivery', 9.50, 1);
INSERT INTO MERCHANT (`name`, delivery_fee, cuisine_id) VALUES ('Tuk Tuk Indian Food', 15, 2);
INSERT INTO MERCHANT (`name`, delivery_fee, cuisine_id) VALUES ('Mario Pizza', 15, 3);

INSERT INTO `STATE` (id, `name`) VALUES (1, 'California');
INSERT INTO `STATE` (id, `name`) VALUES (2, 'Texas');
INSERT INTO `STATE` (id, `name`) VALUES (3, 'Florida');

INSERT INTO CITY (id, `name`, state_id) VALUES (1, 'Los Angeles', 1);
INSERT INTO CITY (id, `name`, state_id) VALUES (2, 'San Francisco', 1);
INSERT INTO CITY (id, `name`, state_id) VALUES (3, 'Dallas', 2);
INSERT INTO CITY (id, `name`, state_id) VALUES (4, 'Houston', 2);
INSERT INTO CITY (id, `name`, state_id) VALUES (5, 'Miami', 3);

INSERT INTO PAYMENT_METHOD (id, description) VALUES (1, 'Credit Card');
INSERT INTO PAYMENT_METHOD (id, description) VALUES (2, 'Debit Card');
INSERT INTO PAYMENT_METHOD (id, description) VALUES (3, 'Cash');

INSERT INTO PERMISSION (id, `name`, description) VALUES (1, 'SEARCH_CUISINES', 'Permits cuisine searching');
INSERT INTO PERMISSION (id, `name`, description) VALUES (2, 'EDIT_CUISINES', 'Permits cuisine editing');

