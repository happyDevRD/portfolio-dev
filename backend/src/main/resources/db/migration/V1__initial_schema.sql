-- Esquema inicial (PostgreSQL). Hibernate valida contra estas tablas en perfil prod.

CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(1000),
    image_url VARCHAR(255),
    project_url VARCHAR(255),
    github_url VARCHAR(255),
    start_date DATE,
    end_date DATE,
    challenge VARCHAR(2000),
    solution VARCHAR(2000)
);

CREATE TABLE project_tags (
    project_id BIGINT NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    tag VARCHAR(255)
);

CREATE TABLE project_features (
    project_id BIGINT NOT NULL REFERENCES projects (id) ON DELETE CASCADE,
    feature VARCHAR(255)
);

CREATE TABLE skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    proficiency INTEGER,
    icon_url VARCHAR(255),
    category VARCHAR(255)
);

CREATE TABLE experiences (
    id BIGSERIAL PRIMARY KEY,
    company VARCHAR(255),
    role VARCHAR(255),
    description VARCHAR(2000),
    start_date DATE,
    end_date DATE,
    current_info BOOLEAN NOT NULL
);

CREATE TABLE experience_highlights (
    experience_id BIGINT NOT NULL REFERENCES experiences (id) ON DELETE CASCADE,
    highlight VARCHAR(500)
);

CREATE TABLE articles (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    slug VARCHAR(255) UNIQUE,
    summary VARCHAR(500),
    content TEXT,
    cover_image_url VARCHAR(255),
    published_at DATE,
    reading_time_minutes INTEGER
);

CREATE TABLE article_tags (
    article_id BIGINT NOT NULL REFERENCES articles (id) ON DELETE CASCADE,
    tag VARCHAR(255)
);

CREATE TABLE portfolio_services (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    icon_url VARCHAR(255)
);

CREATE TABLE testimonials (
    id BIGSERIAL PRIMARY KEY,
    author_name VARCHAR(255),
    author_role VARCHAR(255),
    content VARCHAR(1000),
    avatar_url VARCHAR(255)
);

CREATE TABLE contacts (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    message TEXT,
    created_at TIMESTAMP
);
