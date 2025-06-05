CREATE TABLE IF NOT EXISTS catalog_management.product_attributes (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    attribute_id BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_product_attribute_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_product_attribute_attribute
        FOREIGN KEY (attribute_id)
        REFERENCES attributes(id)
        ON DELETE CASCADE
);