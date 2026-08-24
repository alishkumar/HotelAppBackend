CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Default Admin Account (Username: admin, Password: admin123)
INSERT INTO users (username, password, role, created_at, updated_at)
VALUES ('admin', '$2a$10$Ml3ICNlYdAtI49XZ7WvwMOJQeMxkQi/rYOmm8YlxkyJTsWCLpfGNm', 'ADMIN', NOW(), NOW());
