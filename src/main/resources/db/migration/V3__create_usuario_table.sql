CREATE TABLE IF NOT EXISTS usuario (
    usu_id SERIAL PRIMARY KEY,
    usu_username VARCHAR(100) NOT NULL UNIQUE,
    usu_password VARCHAR(255) NOT NULL,
    usu_roles VARCHAR(255) NOT NULL
);
