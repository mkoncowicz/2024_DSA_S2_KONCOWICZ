-- User Table
CREATE TABLE IF NOT EXISTS app_user
(
    id           BIGSERIAL PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    first_name   VARCHAR(255),
    last_name    VARCHAR(255),
    email        VARCHAR(255) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    gender       VARCHAR(255),
    day_of_birth DATE,
    description  TEXT,
    image        VARCHAR(255)
);

-- Post Table
CREATE TABLE IF NOT EXISTS post
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL REFERENCES app_user(id),
    caption     TEXT,
    image_url   VARCHAR(255),
    category    VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    is_private  BOOLEAN NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tag Table
CREATE TABLE IF NOT EXISTS tag
(
    id    BIGSERIAL PRIMARY KEY,
    name  VARCHAR(255) NOT NULL UNIQUE
);

-- Post_Tag Table
CREATE TABLE IF NOT EXISTS post_tag
(
    post_id  BIGINT NOT NULL REFERENCES post(id) ON DELETE CASCADE,
    tag_id   BIGINT NOT NULL REFERENCES tag(id) ON DELETE CASCADE,
    PRIMARY KEY (post_id, tag_id)
);

-- Comment Table
CREATE TABLE IF NOT EXISTS comment
(
    id         BIGSERIAL PRIMARY KEY,
    post_id    BIGINT NOT NULL REFERENCES post(id),
    user_id    BIGINT NOT NULL REFERENCES app_user(id),
    text       TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Post Reaction Table
CREATE TABLE IF NOT EXISTS post_reaction
(
    id          BIGSERIAL PRIMARY KEY,
    post_id     BIGINT NOT NULL REFERENCES post(id),
    user_id     BIGINT NOT NULL REFERENCES app_user(id),
    reaction    VARCHAR(50) NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (post_id, user_id, reaction)
);