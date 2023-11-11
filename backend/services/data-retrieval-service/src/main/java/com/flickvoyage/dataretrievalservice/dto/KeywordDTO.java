package com.flickvoyage.dataretrievalservice.dto;

import java.util.List;

import com.flickvoyage.dataretrievalservice.entity.Movie;

public record KeywordDTO(
    long keywordId,
    String keyword,
    List<Movie> movieList
) {}
