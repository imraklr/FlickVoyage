package com.flickvoyage.dataretrievalservice.services.filter;

import java.util.List;

import com.flickvoyage.dataretrievalservice.dto.CastInfoDTO;

/**
 * @implNote Here the convention for method name is [action, category/object name, options/conditions, names, parameters]. For example 
 * "adult" represents category, "Choice" represents option/condition which represents a boolean choice of true/false, 
 * "Movies" represents table name and "InRange" represents the parameters. These are not a stricter convention.
 * 
 * @author Rakesh Kumar
 */
public interface CastService {
    List<CastInfoDTO> getCastInfoByMovieId(int movieId);
}
