CREATE TABLE supplier (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE customer (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL
);

CREATE TABLE offer (
   id BIGSERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   description TEXT,
   price DECIMAL(10,2) NOT NULL,

   supplier_id BIGINT NOT NULL,
   customer_id BIGINT NOT NULL,

   status VARCHAR(50) NOT NULL,
   created_at TIMESTAMP NOT NULL,

   CONSTRAINT fk_offer_supplier
       FOREIGN KEY (supplier_id)
           REFERENCES supplier(id),

   CONSTRAINT fk_offer_customer
       FOREIGN KEY (customer_id)
           REFERENCES customer(id)
);
