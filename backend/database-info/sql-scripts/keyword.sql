SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS keyword;

CREATE TABLE keyword (
    `keyword_id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `keyword` VARCHAR(255)
);

SET FOREIGN_KEY_CHECKS = 1;