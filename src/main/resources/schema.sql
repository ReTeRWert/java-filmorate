DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS films CASCADE;
DROP TABLE IF EXISTS genres CASCADE;
DROP TABLE IF EXISTS rating_mpa CASCADE;
DROP TABLE IF EXISTS film_genres CASCADE;
DROP TABLE IF EXISTS likes CASCADE;
DROP TABLE IF EXISTS friends CASCADE;


CREATE TABLE IF NOT EXISTS rating_mpa (
    rating_id       int PRIMARY KEY auto_increment,
    rating_name     varchar(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres (
    genre_id        int PRIMARY KEY auto_increment,
    genre_name      varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    film_id             int PRIMARY KEY auto_increment,
    film_name           varchar(100) NOT NULL,
    film_description    varchar(200) NOT NULL,
    release_date        date NOT NULL,
    duration            int NOT NULL,
    rating_id           int NOT NULL REFERENCES rating_mpa(rating_id),
    rate                int
);

CREATE TABLE IF NOT EXISTS film_genres (
    film_id         int REFERENCES films(film_id),
    genre_id        int REFERENCES genres(genre_id),
    PRIMARY KEY (film_id, genre_id),
    UNIQUE(genre_id)
);


CREATE TABLE IF NOT EXISTS users (
    user_id         int PRIMARY KEY auto_increment,
    email           varchar(70) NOT NULL,
    login           varchar(100) NOT NULL,
    user_name       varchar(200),
    birthday        date NOT NULL,
    UNIQUE(email),
    UNIQUE(login)
);

CREATE TABLE IF NOT EXISTS likes (
    user_id         int REFERENCES users(user_id),
    film_id         int REFERENCES films(film_id),
    PRIMARY KEY(user_id, film_id)
);

CREATE TABLE IF NOT EXISTS friends (
    user_id         int REFERENCES users(user_id),
    friend_id       int REFERENCES users(user_id),
    PRIMARY KEY(user_id, friend_id)
);