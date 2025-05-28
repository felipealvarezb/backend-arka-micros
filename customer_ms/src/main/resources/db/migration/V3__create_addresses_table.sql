CREATE TABLE IF NOT EXISTS user_management.addresses (
  id          BIGSERIAL PRIMARY KEY,
  address     TEXT                    NOT NULL,
  city        VARCHAR(50)             NOT NULL,
  state       VARCHAR(50),
  country     VARCHAR(50),
  zip_code    VARCHAR(20),
  user_id     BIGINT                  NOT NULL,
  created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_at  TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT fk_addresses_user
    FOREIGN KEY (user_id)
    REFERENCES user_management.users(id)
    ON DELETE CASCADE
);