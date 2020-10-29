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
 "phone_number"     text NOT NULL,
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

INSERT INTO address VALUES (DEFAULT, 'Poland', 'Cracov', '31-476', 'Grodzka', 1);
SELECT pg_catalog.setval('address_id_seq', 1, true);

INSERT INTO user_account VALUES (DEFAULT, 'Bob', 'soriusz15@gmail.com', '111221222', 1, 1);
SELECT pg_catalog.setval('user_account_id_seq', 1, true);

INSERT INTO supplier VALUES (DEFAULT, 'Amazon', 'Description of the Amazon', 'America');
INSERT INTO supplier VALUES (DEFAULT, 'Lenovo', 'Description of the Lenovo', 'China');
INSERT INTO supplier VALUES (DEFAULT, 'Apple', 'Description of the Apple', 'America');
SELECT pg_catalog.setval('supplier_id_seq', 3, true);

INSERT INTO product_category VALUES (DEFAULT, 'Tablet');
INSERT INTO product_category VALUES (DEFAULT, 'Phone');
INSERT INTO product_category VALUES (DEFAULT, 'PC');
INSERT INTO product_category VALUES (DEFAULT, 'Laptop');
SELECT pg_catalog.setval('product_category_id_seq', 2, true);

INSERT INTO product VALUES (DEFAULT, 'x100', 'moj opis', '50.0', 'EURO', 2, 2, '');
INSERT INTO product VALUES (DEFAULT, 'abc', 'moj opis 2', '10.0', 'EURO', 1, 1, '');
SELECT pg_catalog.setval('product_id_seq', 2, true);

INSERT INTO cart VALUES (DEFAULT, 1);
SELECT pg_catalog.setval('cart_id_seq', 1, true);

INSERT INTO cart_content VALUES (DEFAULT, 1, 1, 3);
INSERT INTO cart_content VALUES (DEFAULT, 2, 1, 2);
SELECT pg_catalog.setval('cart_content_id_seq', 3, true);

INSERT INTO user_order VALUES (DEFAULT, 1, false);
SELECT pg_catalog.setval('user_order_id_seq', 1, true);
