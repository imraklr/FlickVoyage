SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS casts;

CREATE TABLE casts (
    `cast_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `movie_id` BIGINT,
    `department_id` INT,
    `name` VARCHAR(255),
    `character` TEXT,

    FOREIGN KEY (`movie_id`) REFERENCES movies(`movie_id`)
);

SET FOREIGN_KEY_CHECKS = 1;