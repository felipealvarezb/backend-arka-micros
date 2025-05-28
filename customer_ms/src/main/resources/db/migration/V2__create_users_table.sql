CREATE TABLE IF NOT EXISTS user_management.users (
  id            BIGSERIAL PRIMARY KEY,
  first_name    VARCHAR(100)           NOT NULL,
  last_name     VARCHAR(100)           NOT NULL,
  phone         VARCHAR(20)            NOT NULL,
  email         VARCHAR(100)           NOT NULL UNIQUE,
  dni           VARCHAR(20)            NOT NULL,
  password      VARCHAR(255)           NOT NULL,
  role_id       BIGINT                 NOT NULL,
  is_active     BOOLEAN                NOT NULL,
  created_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_at    TIMESTAMP WITHOUT TIME ZONE,
  CONSTRAINT fk_users_role
    FOREIGN KEY (role_id)
    REFERENCES user_management.roles(id)
    ON DELETE RESTRICT
);