# OPTIONAL: Ignore insert if any errors
# INSERT IGNORE INTO CUISINE(id, `name`) VALUES (1, 'Thai');
# INSERT IGNORE INTO CUISINE(id, `name`) VALUES (2, 'Indian');
# INSERT IGNORE INTO CUISINE(id, `name`) VALUES (3, 'Argentine');
# INSERT IGNORE INTO CUISINE(id, `name`) VALUES (4, 'Brazilian');

# Remove foreign key checking before delete
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM CITY;
DELETE FROM CUISINE;
DELETE FROM STATE;
DELETE FROM PAYMENT_METHOD;
DELETE FROM GROUP$;
DELETE FROM PERMISSION_GROUP;
DELETE FROM PERMISSION$;
DELETE FROM PRODUCT;
DELETE FROM MERCHANT;
DELETE FROM MERCHANT_PAYMENT_METHOD;
DELETE FROM USER;
DELETE FROM USER_GROUP;

# Restore foreign key checking
SET FOREIGN_KEY_CHECKS = 1;

# Resets auto-increment
ALTER TABLE CITY AUTO_INCREMENT = 1;
ALTER TABLE CUISINE AUTO_INCREMENT = 1;
ALTER TABLE STATE AUTO_INCREMENT = 1;
ALTER TABLE PAYMENT_METHOD AUTO_INCREMENT = 1;
ALTER TABLE GROUP$ AUTO_INCREMENT = 1;
ALTER TABLE PERMISSION$ AUTO_INCREMENT = 1;
ALTER TABLE PRODUCT AUTO_INCREMENT = 1;
ALTER TABLE MERCHANT AUTO_INCREMENT = 1;
ALTER TABLE USER AUTO_INCREMENT = 1;

# Assemble test data
INSERT INTO CUISINE(id, `name`) VALUES (1, 'Thai');
INSERT INTO CUISINE(id, `name`) VALUES (2, 'Indian');
INSERT INTO CUISINE(id, `name`) VALUES (3, 'Argentine');
INSERT INTO CUISINE(id, `name`) VALUES (4, 'Brazilian');

INSERT INTO STATE (id, `name`) VALUES (1, 'California');
INSERT INTO STATE (id, `name`) VALUES (2, 'Texas');
INSERT INTO STATE (id, `name`) VALUES (3, 'Florida');

INSERT INTO CITY (id, `name`, state_id) VALUES (1, 'Los Angeles', 1);
INSERT INTO CITY (id, `name`, state_id) VALUES (2, 'San Francisco', 1);
INSERT INTO CITY (id, `name`, state_id) VALUES (3, 'Dallas', 2);
INSERT INTO CITY (id, `name`, state_id) VALUES (4, 'Houston', 2);
INSERT INTO CITY (id, `name`, state_id) VALUES (5, 'Miami', 3);

INSERT INTO MERCHANT (id, `name`, delivery_fee, cuisine_id, registration_date, update_date, active, address_zip_code, address_street, address_number, address_neighborhood, address_city_id) VALUES (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '00000-000', 'Sunset Boulevard', '000', 'Sunset Strip', 1);
INSERT INTO MERCHANT (id, `name`, delivery_fee, cuisine_id, registration_date, update_date, active) VALUES (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, 1);
INSERT INTO MERCHANT (id, `name`, delivery_fee, cuisine_id, registration_date, update_date, active) VALUES (3, 'Tuk Tuk Indian Food', 15, 2, utc_timestamp, utc_timestamp, 1);
INSERT INTO MERCHANT (id, `name`, delivery_fee, cuisine_id, registration_date, update_date, active) VALUES (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, 1);
INSERT INTO MERCHANT (id, `name`, delivery_fee, cuisine_id, registration_date, update_date, active) VALUES (5, 'Uncle Sam', 11, 4, utc_timestamp, utc_timestamp, 1);

INSERT INTO PAYMENT_METHOD (id, description) VALUES (1, 'Credit Card');
INSERT INTO PAYMENT_METHOD (id, description) VALUES (2, 'Debit Card');
INSERT INTO PAYMENT_METHOD (id, description) VALUES (3, 'Cash');

INSERT INTO PERMISSION$ (id, `name`, description) VALUES (1, 'SEARCH_CUISINES', 'Permits cuisine searching');
INSERT INTO PERMISSION$ (id, `name`, description) VALUES (2, 'EDIT_CUISINES', 'Permits cuisine editing');

INSERT INTO MERCHANT_PAYMENT_METHOD (merchant_id, payment_method_id) VALUES (1, 1), (1, 2), (1, 3), (2,3), (3, 2), (3, 3);

INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Pork with sweet and sour sauce', 'Delicious pork with special sauce', 78.90, 1, 1);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Thai prawns', '16 prawns in hot sauce', 110, 1, 1);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Spicy salad with grilled meat', 'Leaf salad with fine cuts of grilled beef and our special red pepper sauce', 87.20, 1, 2);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Garlic Naan', 'Traditional Indian bread with garlic topping', 21, 1, 3);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Murg Curry', 'Chicken cubes prepared with curry sauce and spices', 43, 1, 3);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Ancho steak', 'Soft and juicy cut, two fingers thick, taken from the front of the ribeye', 79, 1, 4);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('T-Bone', 'Very tasty cut, with a T-shaped bone, with the filet on one side and the filet mignon on the other.', 89, 1, 4);
INSERT INTO PRODUCT (`name`, description, price, active, merchant_id) VALUES ('Hamburger', 'Sandwich with lots of cheese, beef hamburger, bacon, egg, salad and mayonnaise', 19, 1, 5);
