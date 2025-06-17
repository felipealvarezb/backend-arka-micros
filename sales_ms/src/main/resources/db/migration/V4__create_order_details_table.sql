CREATE TABLE IF NOT EXISTS order_management.order_details (
    id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    sub_total DECIMAL(10, 2) NOT NULL,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_order_details_order
            FOREIGN KEY (order_id)
            REFERENCES orders(id)
            ON DELETE CASCADE
);