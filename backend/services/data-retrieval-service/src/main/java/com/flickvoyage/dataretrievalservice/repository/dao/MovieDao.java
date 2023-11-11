package com.flickvoyage.dataretrievalservice.repository.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.flickvoyage.dataretrievalservice.entity.Movie;

/**
 * @author Rakesh Kumar
 */
public interface MovieDao {

    // methods for singular requests goes here

    // methods that need to be accessed for initial display of items on the web page goes here
    List<Movie> getMoviesForPage(PageRequest pageRequest);

    // method that return strings of unique languages present in the database
    List<String> getAllUniqueLanguages();

    // method that returns complete genre list
    List<String> getAllGenres();

    // method that returns lower bound as well as the upper bound of the release year
    Integer releaseYearLowerBound();
    Integer releaseYearUpperBound();
    Integer popularityScoreLowerBound();
    Integer popularityScoreUpperBound();
    List<String> getRandomKeywords();
    List<String> getRandomCasts();
}
