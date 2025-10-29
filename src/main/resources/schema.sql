CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY,
    alias VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS blog_post (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    user_id UUID,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS support_response (
    id UUID PRIMARY KEY,
    text TEXT NOT NULL,
    support_request UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS customer_request (
    id UUID PRIMARY KEY,
    text TEXT NOT NULL,
    support_response UUID NOT NULL,
    customer_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS validation_result (
    id UUID PRIMARY KEY,
    content_id UUID NOT NULL,
    content_type VARCHAR(255) NOT NULL,
    is_valid BOOLEAN NOT NULL,
    errors TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
