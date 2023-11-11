package com.flickvoyage.dataretrievalservice.repository.dao.implementation;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.flickvoyage.dataretrievalservice.entity.Cast;
import com.flickvoyage.dataretrievalservice.repository.dao.CastDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class CastDaoImpl implements CastDao {
    private EntityManager entityManager;

    public CastDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Cast> getCastInfoByMovieId(int mId) {
        Query query = entityManager.createNativeQuery(
            """
                SELECT * FROM cast WHERE movie_id = :mId
            """,
            Cast.class
        );
        query.setParameter("mId", mId);

        @SuppressWarnings(value = "unchecked")
        List<Cast> castList = query.getResultList();

        return castList;
    }
}
