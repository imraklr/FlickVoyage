SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS department;

CREATE TABLE department (
    `department_id` INT AUTO_INCREMENT,
    `name` VARCHAR(255) UNIQUE,
    PRIMARY KEY (department_id)
);


SET FOREIGN_KEY_CHECKS = 1;