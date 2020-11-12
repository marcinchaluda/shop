DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS cart CASCADE;
DROP TABLE IF EXISTS cart_content CASCADE;
DROP TABLE IF EXISTS user_order CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;
DROP TABLE IF EXISTS supplier CASCADE;
DROP TABLE IF EXISTS product_category CASCADE;

CREATE TABLE "supplier"
(
    "id"           serial PRIMARY KEY,
    "name"         text NOT NULL,
    "description"  text NOT NULL,
    "country"      text NOT NULL
);

CREATE TABLE "product_category"
(
    "id"           serial PRIMARY KEY,
    "name"         text NOT NULL
);

CREATE TABLE "product"
(
 "id"           serial PRIMARY KEY,
 "product_name" text NOT NULL,
 "description"  text NOT NULL,
 "unit_price"   double precision NOT NULL,
 "currency"     text NOT NULL,
 "category"     integer NOT NULL,
 "supplier"     integer NOT NULL,
 "image_source" text,
 CONSTRAINT "FK_411" FOREIGN KEY ( "category" ) REFERENCES "product_category" ( "id" ),
 CONSTRAINT "FK_412" FOREIGN KEY ( "supplier" ) REFERENCES "supplier" ( "id" )
);

CREATE INDEX "fkIdx_411" ON "product"
    (
     "category"
        );

CREATE INDEX "fkIdx_412" ON "product"
    (
     "supplier"
        );

CREATE TABLE "address"
(
 "id"           serial PRIMARY KEY,
 "country"      text NOT NULL,
 "city"         text NOT NULL,
 "zip_code"     text NOT NULL,
 "street"       text NOT NULL,
 "local_number" integer NOT NULL
);

CREATE TABLE "user_account"
(
 "id"               serial PRIMARY KEY,
 "full_name"        text NOT NULL,
 "email"            text NOT NULL,
 "password"         text NOT NULL,
 "phone_number"     text,
 "billing_address"  integer NOT NULL,
 "shipping_address" integer NOT NULL,
 CONSTRAINT "FK_47" FOREIGN KEY ( "billing_address" ) REFERENCES "address" ( "id" ),
 CONSTRAINT "FK_50" FOREIGN KEY ( "shipping_address" ) REFERENCES "address" ( "id" )
);

CREATE INDEX "fkIdx_47" ON "user_account"
(
 "billing_address"
);

CREATE INDEX "fkIdx_50" ON "user_account"
(
 "shipping_address"
);

CREATE TABLE "cart"
(
 "id"      serial PRIMARY KEY,
 "user_id" integer NOT NULL,
 CONSTRAINT "FK_67" FOREIGN KEY ( "user_id" ) REFERENCES "user_account" ( "id" )
);

CREATE INDEX "fkIdx_67" ON "cart"
(
 "user_id"
);

CREATE TABLE "cart_content"
(
 "id"         serial PRIMARY KEY,
 "product_id" integer NOT NULL,
 "cart_id"    integer NOT NULL,
 "quantity"   int NOT NULL,
 CONSTRAINT "FK_22" FOREIGN KEY ( "product_id" ) REFERENCES "product" ( "id" ),
 CONSTRAINT "FK_25" FOREIGN KEY ( "cart_id" ) REFERENCES "cart" ( "id" )
);

CREATE INDEX "fkIdx_22" ON "cart_content"
(
 "product_id"
);

CREATE INDEX "fkIdx_25" ON "cart_content"
(
 "cart_id"
);

CREATE TABLE "user_order"
(
 "id"      serial PRIMARY KEY,
 "cart_id" integer NOT NULL,
 "paid"    boolean NOT NULL,
 CONSTRAINT "FK_62" FOREIGN KEY ( "cart_id" ) REFERENCES "cart" ( "id" )
);

CREATE INDEX "fkIdx_62" ON "user_order"
(
 "cart_id"
);

INSERT INTO address VALUES (DEFAULT, '', '', '00-000', '', 0);
INSERT INTO address VALUES (DEFAULT, 'Poland', 'Cracov', '31-476', 'Grodzka', 1);
INSERT INTO address VALUES (DEFAULT, 'Poland', 'Cracov', '31-476', 'Grodzka', 1);
SELECT pg_catalog.setval('address_id_seq', 3, true);

INSERT INTO user_account VALUES (DEFAULT, 'Bob', 'soriusz15@gmail.com', '123', '111221222', 1, 2);
SELECT pg_catalog.setval('user_account_id_seq', 1, true);

INSERT INTO supplier VALUES (DEFAULT, 'Amazon', 'Description of the Amazon', 'America');
INSERT INTO supplier VALUES (DEFAULT, 'Lenovo', 'Description of the Lenovo', 'China');
INSERT INTO supplier VALUES (DEFAULT, 'Apple', 'Description of the Apple', 'America');
SELECT pg_catalog.setval('supplier_id_seq', 3, true);

INSERT INTO product_category VALUES (DEFAULT, 'Tablet');
INSERT INTO product_category VALUES (DEFAULT, 'Phone');
INSERT INTO product_category VALUES (DEFAULT, 'Notebook');
INSERT INTO product_category VALUES (DEFAULT, 'Web-device');
SELECT pg_catalog.setval('product_category_id_seq', 4, true);

INSERT INTO product VALUES (DEFAULT, 'IPhone 11', 'IPhone 11 description', '100', 'EURO', 2, 3, 'https://media-play.pl/ecommerce/medias/productimages/47708/TE-AP-1164G-PL1-GN/square.png');
INSERT INTO product VALUES (DEFAULT, 'Mac Book Air', 'Mac Book Air description', '200', 'EURO', 3, 3, 'https://image.ceneostatic.pl/data/products/52404834/i-apple-macbook-air-13-3-i5-8gb-128gb-macos-srebrny-mqd32zea.jpg');
INSERT INTO product VALUES (DEFAULT, 'IdeaPad3', 'IdeaPad3 description', '130', 'EURO', 3, 2, 'https://image.ceneostatic.pl/data/products/95943535/i-lenovo-ideapad-3-15ada05-15-6-ryzen5-8gb-256gb-noos-81w100b8pb.jpg');
INSERT INTO product VALUES (DEFAULT, 'Lenovo K5', 'Lenovo K5 description', '10.0', 'EURO', 2, 2, 'https://lenovozone.pl/produkty/wp-content/uploads/2016/05/lenovo-smartphone-vibe-k5-hero-450x320.png');
INSERT INTO product VALUES (DEFAULT, 'Kindle', 'Kindle description', '80', 'EURO', 4, 1, 'https://5.imimg.com/data5/RK/FL/MY-33422787/kindle-e-book-reader-500x500.jpg');
INSERT INTO product VALUES (DEFAULT, 'Lenovo Yoga', 'Lenovo Yoga description', '140', 'EURO', 4, 2, 'https://i0.wp.com/cdnssl.ubergizmo.com/wp-content/uploads/2018/12/Lenovo-Yoga-Book-C930-29.jpg');
INSERT INTO product VALUES (DEFAULT, 'Lenovo Tab', 'Lenovo Tab description', '35', 'EURO', 1, 2, 'https://image.ceneostatic.pl/data/products/91492431/i-lenovo-tab-m8-tb-8505f-8-2-32gb-lte-szary-za5h0062pl.jpg');
INSERT INTO product VALUES (DEFAULT, 'IPhone 8', 'IPhone 8 description', '11', 'EURO', 2, 3, 'https://image.ceneostatic.pl/data/products/55424279/i-apple-iphone-8-64gb-srebrny.jpg');
SELECT pg_catalog.setval('product_id_seq', 8, true);

INSERT INTO cart VALUES (DEFAULT, 1);
SELECT pg_catalog.setval('cart_id_seq', 1, true);

INSERT INTO cart_content VALUES (DEFAULT, 1, 1, 3);
INSERT INTO cart_content VALUES (DEFAULT, 2, 1, 2);
SELECT pg_catalog.setval('cart_content_id_seq', 3, true);

INSERT INTO user_order VALUES (DEFAULT, 1, false);
SELECT pg_catalog.setval('user_order_id_seq', 1, true);
