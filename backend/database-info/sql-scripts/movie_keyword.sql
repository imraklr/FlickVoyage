SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS movie_keyword;

CREATE TABLE movie_keyword (
    `movie_id` BIGINT,
    `keyword_id` BIGINT,

    PRIMARY KEY (`movie_id`, `keyword_id`),
    FOREIGN KEY (`movie_id`) REFERENCES `movie` (`movie_id`),
    FOREIGN KEY (`keyword_id`) REFERENCES `keyword` (`keyword_id`)
);

SET FOREIGN_KEY_CHECKS = 1;