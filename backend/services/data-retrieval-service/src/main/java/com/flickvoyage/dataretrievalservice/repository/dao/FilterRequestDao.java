package com.flickvoyage.dataretrievalservice.repository.dao;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.flickvoyage.dataretrievalservice.entity.Movie;

public interface FilterRequestDao {
    List<Movie> getMovieInfoSnapshotListForFilterWithPage(PageRequest pageRequest, String queryString);
}
