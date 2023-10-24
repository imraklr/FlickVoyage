SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS cast_department;

CREATE TABLE cast_department (
    `cast_id` BIGINT,
    `department_id` INT,

    PRIMARY KEY (`cast_id`, `department_id`),
    FOREIGN KEY (`cast_id`) REFERENCES casts(`cast_id`),
    FOREIGN KEY (`department_id`) REFERENCES departments(`department_id`)
);

SET FOREIGN_KEY_CHECKS = 1;