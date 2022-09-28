DROP TABLE IF EXISTS genres;
CREATE TABLE IF NOT EXISTS genres (
                                      genre_id INT PRIMARY KEY,
                                      genre_name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS ratings;
CREATE TABLE IF NOT EXISTS ratings (
                                       rating_id INT PRIMARY KEY,
                                       rating_name VARCHAR(100) NOT NULL
);

DROP TABLE IF EXISTS films;
CREATE TABLE IF NOT EXISTS films (
                                     film_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     film_name VARCHAR(100) NOT NULL,
                                     description VARCHAR(200),
                                     release_date DATE,
                                     duration BIGINT,
                                     rating INT REFERENCES ratings(rating_id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
                                     user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     email VARCHAR(100),
                                     login VARCHAR(100),
                                     user_name VARCHAR(100),
                                     birthday DATE
);

DROP TABLE IF EXISTS friendship;
CREATE TABLE IF NOT EXISTS friendship (
                                          user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                                          friend_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                                          CONSTRAINT composite_friendship_key PRIMARY KEY(user_id, friend_id)
);

DROP TABLE IF EXISTS film_genres;
CREATE TABLE IF NOT EXISTS film_genres (
                                           film_id BIGINT NOT NULL REFERENCES films(film_id) ON DELETE CASCADE,
                                           genre_id BIGINT NOT NULL REFERENCES genres(genre_id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS likes;
CREATE TABLE IF NOT EXISTS likes (
                                     film_id BIGINT NOT NULL REFERENCES films(film_id) ON DELETE CASCADE,
                                     user_id BIGINT NOT NULL REFERENCES users(user_id) ON DELETE CASCADE,
                                     CONSTRAINT composite_likes_key PRIMARY KEY (film_id, user_id)
);