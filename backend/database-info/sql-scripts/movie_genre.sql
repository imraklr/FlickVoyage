SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `movie_genre`;

CREATE TABLE movie_genre (
    `movie_id` BIGINT,
    `genre_id` INT,
    PRIMARY KEY (`movie_id`, `genre_id`),
    FOREIGN KEY (`movie_id`) REFERENCES movie(`movie_id`),
    FOREIGN KEY (`genre_id`) REFERENCES genre(`genre_id`)
);

SET FOREIGN_KEY_CHECKS = 1;