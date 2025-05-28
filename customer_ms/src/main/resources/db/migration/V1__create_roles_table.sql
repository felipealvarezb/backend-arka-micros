CREATE TABLE IF NOT EXISTS user_management.roles (
  id          BIGSERIAL PRIMARY KEY,
  name        VARCHAR(50)             NOT NULL UNIQUE,
  description TEXT,
  created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT now(),
  updated_at  TIMESTAMP WITHOUT TIME ZONE
);