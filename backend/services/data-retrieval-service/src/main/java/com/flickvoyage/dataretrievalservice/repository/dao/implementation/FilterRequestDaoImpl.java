package com.flickvoyage.dataretrievalservice.repository.dao.implementation;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.flickvoyage.dataretrievalservice.entity.Movie;
import com.flickvoyage.dataretrievalservice.repository.dao.FilterRequestDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class FilterRequestDaoImpl implements FilterRequestDao {
    private EntityManager entityManager;

    public FilterRequestDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Movie> getMovieInfoSnapshotListForFilterWithPage(PageRequest pageRequest, String queryString) {
        Query query = entityManager.createNativeQuery(queryString, Movie.class);
        query.setFirstResult((pageRequest.getPageNumber()) * pageRequest.getPageSize());
        query.setMaxResults(pageRequest.getPageSize());

        @SuppressWarnings(value = "unchecked")
        List<Movie> movies = query.getResultList();

        return movies;
    }
}
