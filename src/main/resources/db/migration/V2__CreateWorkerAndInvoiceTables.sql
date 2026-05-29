CREATE TABLE supplier (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    services VARCHAR(255) NOT NULL ,
    createdAt TIMESTAMP NOT NULL
);

CREATE TABLE worker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    supplierId BIGINT,
    createdAt TIMESTAMP NOT NULL,

    CONSTRAINT fk_worker_supplier
        FOREIGN KEY (supplierId)
            REFERENCES supplier(id)
);

CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    createdAt TIMESTAMP NOT NULL
);

CREATE TABLE offer (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,

    supplierId BIGINT NOT NULL,
    customerId BIGINT NOT NULL,

    status VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP NOT NULL,

    CONSTRAINT fk_offer_supplier
       FOREIGN KEY (supplierId)
           REFERENCES supplier(id),

    CONSTRAINT fk_offer_customer
       FOREIGN KEY (customerId)
           REFERENCES customer(id)
);

CREATE TABLE invoice (
     id BIGINT PRIMARY KEY,
     supplierId BIGINT NOT NULL,
     customerId BIGINT NOT NULL,
     offerId BIGINT NOT NULL,
     invoiceNumber BIGINT NOT NULL,
     amount DECIMAL(10, 2) NOT NULL,
     issuedAt TIMESTAMP NOT NULL,
     dueDate TIMESTAMP NOT NULL,
     status VARCHAR(50) NOT NULL,
     createdAt TIMESTAMP NOT NULL,

     CONSTRAINT fk_invoice_supplier
         FOREIGN KEY (supplierId)
             REFERENCES supplier(id),

     CONSTRAINT fk_invoice_customer
         FOREIGN KEY (customerId)
             REFERENCES customer(id),

     CONSTRAINT fk_invoice_offer
         FOREIGN KEY (offerId)
             REFERENCES offer(id)
);