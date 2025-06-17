CREATE TABLE IF NOT EXISTS order_management.cart_details (
    id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL,
    sub_total DECIMAL(10,2) NOT NULL,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_cart_details_cart
            FOREIGN KEY (cart_id)
            REFERENCES carts(id)
            ON DELETE CASCADE
);