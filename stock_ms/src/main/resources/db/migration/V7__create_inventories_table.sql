CREATE TABLE IF NOT EXISTS catalog_management.inventories (
    id BIGSERIAL PRIMARY KEY,
    actual_stock INT NOT NULL,
    min_stock INT NOT NULL,
    product_id BIGINT NOT NULL,
    country_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_inventory_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_inventory_country
        FOREIGN KEY (country_id)
        REFERENCES countries(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_inventory_supplier
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(id)
        ON DELETE CASCADE
);