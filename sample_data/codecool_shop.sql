DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS cart CASCADE;
DROP TABLE IF EXISTS cart_content CASCADE;
DROP TABLE IF EXISTS user_order CASCADE;
DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;
DROP TABLE IF EXISTS user_carts CASCADE;

CREATE TABLE "product"
(
 "id"           serial PRIMARY KEY,
 "product_name" text NOT NULL,
 "unit_price"   double precision NOT NULL,
 "currency"     text NOT NULL,
 "category"     text NOT NULL,
 "supplier"     text NOT NULL
);

CREATE TABLE "cart"
(
 "id"       serial PRIMARY KEY,
 "quantity" int NOT NULL
);

CREATE TABLE "cart_content"
(
 "id"         serial PRIMARY KEY,
 "product_id" integer NOT NULL,
 "cart_id"    integer NOT NULL,
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

CREATE TABLE "address"
(
 "id"           serial PRIMARY KEY,
 "country"      text NOT NULL,
 "city"         text NOT NULL,
 "zip_code"     text NOT NULL,
 "street"       text NOT NULL,
 "local_number" int NOT NULL
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



CREATE TABLE "user_carts"
(
 "id"      serial PRIMARY KEY,
 "user_id" integer NOT NULL,
 "cart_id" integer NOT NULL,
 CONSTRAINT "FK_53" FOREIGN KEY ( "user_id" ) REFERENCES "user_account" ( "id" ),
 CONSTRAINT "FK_56" FOREIGN KEY ( "cart_id" ) REFERENCES "cart" ( "id" )
);

CREATE INDEX "fkIdx_53" ON "user_carts"
(
 "user_id"
);

CREATE INDEX "fkIdx_56" ON "user_carts"
(
 "cart_id"
);

INSERT INTO address VALUES (DEFAULT, 'Poland', 'Cracov', '31-476', 'Grodzka', 1);
SELECT pg_catalog.setval('address_id_seq', 1, true);

INSERT INTO user_account VALUES (DEFAULT, 'Bob', 'soriusz15@gmail.com', '111221222', 1, 1);
SELECT pg_catalog.setval('user_account_id_seq', 1, true);

INSERT INTO product VALUES (DEFAULT, 'x100', '50.0', 'EURO', 'tablet', 'samsung');
INSERT INTO product VALUES (DEFAULT, 'abc', '10.0', 'EURO', 'tablet', 'huawei');
SELECT pg_catalog.setval('product_id_seq', 2, true);

