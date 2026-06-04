ALTER TABLE supplier
    ADD COLUMN description VARCHAR(255);

ALTER TABLE supplier
    ADD COLUMN services VARCHAR(255);

CREATE TABLE worker (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    supplier_id BIGINT,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_worker_supplier
        FOREIGN KEY (supplier_id)
            REFERENCES supplier(id)
);

CREATE TABLE invoice (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    offer_id BIGINT NOT NULL,
    invoice_number BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    issued_at TIMESTAMP NOT NULL,
    due_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_invoice_supplier
        FOREIGN KEY (supplier_id)
            REFERENCES supplier(id),

    CONSTRAINT fk_invoice_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer(id),

    CONSTRAINT fk_invoice_offer
        FOREIGN KEY (offer_id)
            REFERENCES offer(id)
);