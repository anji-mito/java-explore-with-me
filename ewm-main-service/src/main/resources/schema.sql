CREATE TABLE IF NOT EXISTS categories (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT UQ_NAME UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS users (
    id    SERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT UQ_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations (
    id  SERIAL PRIMARY KEY,
    lat REAL,
    lon REAL
);

CREATE TABLE IF NOT EXISTS event (
    id                  SERIAL PRIMARY KEY,
    annotation          TEXT,
    category_id         BIGINT,
    confirm_requests    BIGINT,
    created_on          TIMESTAMP,
    description         TEXT,
    event_date          TIMESTAMP,
    initiator_id        BIGINT,
    location_id         BIGINT,
    paid                BOOLEAN,
    participant_limit   INT,
    published_on        TIMESTAMP,
    request_moderation  BOOLEAN,
    state               VARCHAR(50),
    title               VARCHAR(254),
    views               BIGINT,
    CONSTRAINT FK_category_id FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT FK_initiator_id FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT FK_location_id FOREIGN KEY (location_id) REFERENCES locations (id)
);