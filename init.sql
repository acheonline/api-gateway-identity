DROP TABLE IF EXISTS news.users;
CREATE SCHEMA IF NOT EXISTS news;
CREATE TABLE IF NOT EXISTS news.users
(
    id       uuid PRIMARY KEY not null,
    username text UNIQUE NOT NULL,
    password text UNIQUE NOT NULL,
    email    text UNIQUE NOT NULL
);