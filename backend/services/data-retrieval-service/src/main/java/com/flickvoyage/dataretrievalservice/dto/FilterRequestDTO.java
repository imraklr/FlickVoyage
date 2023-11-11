package com.flickvoyage.dataretrievalservice.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) representing filtering criteria for movie queries.
 *
 * @author Rakesh Kumar
 */
public record FilterRequestDTO(
    List<String> languages,
    Boolean adult,
    Float popularityScoreLowerBound,
    Float popularityScoreUpperBound,
    Integer releaseYearLowerBound,
    Integer releaseYearUpperBound,
    List<String> genreList,
    List<String> keywordList,
    List<String> castNameList
) {
    public boolean isValidRecord() {
        return 
            languages != null ||
            adult != null ||
            
            popularityScoreLowerBound!=null || popularityScoreUpperBound != null ||

            releaseYearLowerBound!=null || releaseYearUpperBound!=null ||

            genreList != null || keywordList!=null || castNameList!=null;
    }
}
