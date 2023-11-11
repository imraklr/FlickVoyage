-- SQL query to create movies table in the movie database

CREATE DATABASE IF NOT EXISTS movie_database;
USE movie_database;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS movie;


--
-- Table structure for table `movie`
--
CREATE TABLE movie (
    movie_id BIGINT auto_increment PRIMARY KEY, 
	adult BOOLEAN, 
	language VARCHAR(4), 
	title TEXT, 
	overview TEXT, 
	popularity FLOAT(53, 1) CHECK (popularity >= 0.0 AND popularity <= 100.0), 
	poster_path TEXT, 
	vote_average FLOAT(53, 1) CHECK (vote_average >= 0.0 AND vote_average <= 10.0), 
	vote_count BIGINT, 
	release_year INTEGER
);


-- Specify to check foreign key constraints
SET FOREIGN_KEY_CHECKS = 1;