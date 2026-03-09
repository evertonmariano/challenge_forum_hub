CREATE TYPE user_role AS ENUM (
    'ROLE_STUDENT',
    'ROLE_INSTRUCTOR',
    'ROLE_ADMIN'
);

CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       phone VARCHAR(20),
                       password VARCHAR(255) NOT NULL,
                       role user_role NOT NULL DEFAULT 'ROLE_STUDENT',
                       active BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);
