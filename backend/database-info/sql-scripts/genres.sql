SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
    `genre_id` INT PRIMARY KEY AUTO_INCREMENT,
    `genre` VARCHAR(255) UNIQUE
);

SET FOREIGN_KEY_CHECKS = 1;