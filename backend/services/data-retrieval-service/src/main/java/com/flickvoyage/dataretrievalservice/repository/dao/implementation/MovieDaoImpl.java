package com.flickvoyage.dataretrievalservice.repository.dao.implementation;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.flickvoyage.dataretrievalservice.entity.Movie;
import com.flickvoyage.dataretrievalservice.repository.dao.MovieDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * @author Rakesh Kumar
 */
@Repository
public class MovieDaoImpl implements MovieDao {
    private EntityManager entityManager;


    public MovieDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Movie> getMoviesForPage(PageRequest pageRequest) {
        Query query = entityManager.createNativeQuery(
            """
                SELECT * FROM movie LIMIT :pS OFFSET :ofst
            """,
            Movie.class
        );
        query.setParameter("pS", pageRequest.getPageSize());
        query.setParameter("ofst", pageRequest.getOffset());

        @SuppressWarnings(value = "unchecked")
        List<Movie> movies = query.getResultList();

        return movies;
    }

    @Override
    public List<String> getAllUniqueLanguages() {
        Query query = entityManager.createNativeQuery(
            """
            SELECT DISTINCT language FROM movie
            """,
            String.class
        );

        @SuppressWarnings(value = "unchecked")
        List<String> langs = query.getResultList();

        return langs;
    }

    @Override
    public List<String> getAllGenres() {
        Query query = entityManager.createNativeQuery("SELECT `genre` FROM genre", String.class);

        
        @SuppressWarnings(value = "unchecked")
        List<String> genres = query.getResultList();

        return genres;
    }

    @Override
    public Integer releaseYearLowerBound() {
        Query query = entityManager.createNativeQuery("SELECT MIN(release_year) FROM movie", Integer.class);

        Integer releaseYrLowerBound = (Integer) query.getSingleResult();

        return releaseYrLowerBound;
    }

    @Override
    public Integer releaseYearUpperBound() {
        Query query = entityManager.createNativeQuery("SELECT MAX(release_year) FROM movie", Integer.class);

        Integer releaseYrUpperBound = (Integer) query.getSingleResult();

        return releaseYrUpperBound;
    }

    @Override
    public Integer popularityScoreLowerBound() {
        Query query = entityManager.createNativeQuery("SELECT MIN(popularity) FROM movie", Integer.class);

        Integer popularityScoreLowerBound = (Integer) query.getSingleResult();

        return popularityScoreLowerBound;
    }

    @Override
    public Integer popularityScoreUpperBound() {
        Query query = entityManager.createNativeQuery("SELECT MAX(popularity) FROM movie", Integer.class);

        Integer popularityScoreUpperBound = (Integer) query.getSingleResult();

        return popularityScoreUpperBound;
    }

    @Override
    public List<String> getRandomKeywords() {
        Query query = entityManager.createNativeQuery("SELECT `keyword` FROM keyword ORDER BY RAND() LIMIT 15");
        
        @SuppressWarnings(value = "unchecked")
        List<String> keys = query.getResultList();

        return keys;
    }

    @Override
    public List<String> getRandomCasts() {
        Query query = entityManager.createNativeQuery("SELECT name FROM cast ORDER BY RAND() LIMIT 15");
        
        @SuppressWarnings(value = "unchecked")
        List<String> casts = query.getResultList();

        return casts;
    }
}
