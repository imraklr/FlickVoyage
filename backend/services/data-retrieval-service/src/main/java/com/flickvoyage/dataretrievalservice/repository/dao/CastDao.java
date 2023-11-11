package com.flickvoyage.dataretrievalservice.repository.dao;

import java.util.List;

import com.flickvoyage.dataretrievalservice.entity.Cast;

public interface CastDao {
    public List<Cast> getCastInfoByMovieId(int mId);
}
