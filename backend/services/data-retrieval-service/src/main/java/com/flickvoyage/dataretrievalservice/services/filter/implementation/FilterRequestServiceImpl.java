package com.flickvoyage.dataretrievalservice.services.filter.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flickvoyage.dataretrievalservice.dto.FilterRequestDTO;
import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;
import com.flickvoyage.dataretrievalservice.repository.dao.FilterRequestDao;
import com.flickvoyage.dataretrievalservice.services.filter.FilterRequestService;
import com.flickvoyage.dataretrievalservice.services.filter.MovieService;


/**
 * @author Rakesh Kumar
 */
@Service
public class FilterRequestServiceImpl implements FilterRequestService {
    @Value("${paging.maxsize}")
    private int maxPageSize;

    @Value("${paging.minsize}")
    private int minPageSize;

    PageRequest pageRequest = null;

    private FilterRequestDao filterRequestDao;

    public FilterRequestServiceImpl(
        FilterRequestDao filterRequestDao
    ) {
        this.filterRequestDao = filterRequestDao;
    }

    
    /**
     * Get a list of movie information snapshots that match the filter criteria.
     * 
     * @param filterRequestDTO The filter criteria provided in a DTO.
     * @param pageNumber The page number for pagination.
     * @param pageSize The number of results per page.
     * @return A list of MovieInfoSnapshotDTO objects matching the filter criteria.
     */
    @Override
    public List<MovieInfoSnapshotDTO> getMovieInfoSnapshotListForFilter(FilterRequestDTO filterRequestDTO, int pageNumber, int pageSize) {
        if((!filterRequestDTO.isValidRecord()) || pageSize < minPageSize || pageNumber <= 0)
            return null;
        
        StringBuilder finalQueryStringBuilder = new StringBuilder();

        /**
         * This class rules data process and validation.
         * 
         * Following is a sample query generated by instance methods:

            SELECT movie_filter.movie_id AS movie_id
                ,adult
                ,`language`
                ,title
                ,overview
                ,popularity
                ,poster_path
                ,vote_average
                ,vote_count
                ,release_year
            FROM (
                SELECT *
                FROM movie
                WHERE (
                        (
                            release_year BETWEEN 1500
                                AND 2500
                            )
                        AND (
                            popularity BETWEEN 0
                                AND 1.0
                            )
                        AND `language` IN (
                            'en'
                            ,'hi'
                            ,'ho'
                            ,'ja'
                            )
                        AND adult = FALSE
                        )
                ) AS movie_filter
            INNER JOIN (
                SELECT DISTINCT movie_id
                FROM cast
                WHERE NAME IN (
                        'Al Pacino'
                        ,'Marlon Brando'
                        )
                ) AS cast_filter ON movie_filter.movie_id = cast_filter.movie_id
            INNER JOIN (
                SELECT DISTINCT movie_id
                FROM movie_genre
                WHERE genre_id IN (
                        SELECT genre_id
                        FROM genre
                        WHERE genre IN (
                                'Action'
                                ,'Drama'
                                ,'Romance'
                                )
                        )
                ) AS genre_filter ON movie_filter.movie_id = genre_filter.movie_id
            INNER JOIN (
                SELECT DISTINCT movie_id
                FROM movie_keyword
                WHERE keyword_id IN (
                        SELECT keyword_id
                        FROM keyword
                        WHERE keyword IN (
                                'patriarch'
                                ,'mafia'
                                ,'lawyer'
                                )
                        )
                ) AS keyword_filter ON movie_filter.movie_id = keyword_filter.movie_id;
         */
        final class DataValidationAndQueryGeneration {
            private String AND = " AND ";
            private String BETWEEN = " BETWEEN ";
            private String INNER_JOIN = " INNER JOIN ";

            DataValidationAndQueryGeneration() {
                validateAndAppendDataToString();
            }


            /**
             * Common function accessed for all the validate and append operations
             */
            private void validateAndAppendDataToString() {

                finalQueryStringBuilder.append(validateMovieTableDataAndAppendString());
                StringBuilder castQuery = validateCastTableDataAndAppendString();
                StringBuilder genreQuery = validateGenreDataAndAppendString();
                StringBuilder keywordQuery = validateKeywordDataAndAppendString();

                finalQueryStringBuilder.append(castQuery).append(genreQuery).append(keywordQuery);
            }

            private StringBuilder validateMovieTableDataAndAppendString() {
                boolean andOperatorApplicable = false;
                // This is a fixed string. This has to be put only when one or more than one of the same table column names (fields) are present in the movie table
                // SELECT movie_filter.movie_id AS movie_id, adult, `language`, title, overview, popularity, poster_path, vote_average, vote_count, release_year FROM 
                StringBuilder temp = new StringBuilder();

                temp.append("SELECT movie_filter.movie_id AS movie_id, adult, `language`, title, overview, popularity, poster_path, vote_average, vote_count, release_year FROM ");
                // validate and append accordingly
                List<String> languageList = filterRequestDTO.languages();
                if(languageList!=null && languageList.size()!=0) {
                    if(languageList.size() > 5)
                        languageList = languageList.subList(0, 4);
                    // validation is complete, append now
                    temp.append("(SELECT * FROM movie WHERE ");
                    // `language` IN ('en', 'hi', 'ho', 'ja')
                    temp.append("(`language` IN ('")
                    .append(
                        String.join("', '", languageList)
                    )
                    .append("'))");
                    // AND operator can be added now in the further query string
                    andOperatorApplicable = true;
                }

                if(filterRequestDTO.adult()!=null) {
                    // validation is complete, append now
                    if(andOperatorApplicable)
                        temp.append(AND);
                    else {
                        temp.append("(SELECT * FROM movie WHERE ");
                        andOperatorApplicable = true;
                    }
                    temp.append("(adult = ").append(filterRequestDTO.adult()).append(')');
                }
                Float psLB = filterRequestDTO.popularityScoreLowerBound();
                Float usLB = filterRequestDTO.popularityScoreLowerBound();
                if(
                    psLB!=null && usLB!=null
                    &&
                    psLB > 0 && psLB < 10.0f
                    &&
                    usLB > 0 && usLB < 10.0f
                    &&
                    psLB < usLB
                ) {
                    // validation is complete, append now
                    if(andOperatorApplicable)
                        temp.append(AND);
                    else {
                        temp.append("(SELECT * FROM movie WHERE ");
                        andOperatorApplicable = true;
                    }
                    temp.append("(popularity").append(BETWEEN).append(psLB).append(AND).append(usLB).append(')');
                }
                Integer ryLB = filterRequestDTO.releaseYearLowerBound();
                Integer ryUB = filterRequestDTO.releaseYearUpperBound();
                if(
                    ryLB!=null && ryUB!=null
                    &&
                    ryLB > 0 && ryUB > 0
                    &&
                    ryLB < ryUB
                ) {
                    // validation is complete, append now
                    if(andOperatorApplicable)
                        temp.append(AND);
                    else {
                        temp.append("(SELECT * FROM movie WHERE ");
                        andOperatorApplicable = true;
                    }
                    temp.append("(release_year").append(BETWEEN).append(ryLB).append(AND).append(ryUB).append(')');
                }

                if(andOperatorApplicable)
                    temp.append(") AS movie_filter");
                else
                    temp.append(" (SELECT * FROM movie) AS movie_filter");

                return temp;
            }

            /* 
             * -- This inner join will be appended in the final query string if the genreList is not null or when the genreList does not contain zero elements and contains less than 5 elements
             * -- Strip off to five elements if found guilty
             * INNER JOIN (SELECT DISTINCT movie_id FROM movie_genre WHERE genre_id IN (
             *         SELECT genre_id FROM genre WHERE genre IN ('Action', 'Drama', 'Romance'))) AS genre_filter
             * ON movie_filter.movie_id = genre_filter.movie_id
            */
            private StringBuilder validateGenreDataAndAppendString() {
                StringBuilder temp = new StringBuilder();

                List<String> genreList = filterRequestDTO.genreList();

                if(genreList!=null && genreList.size()!=0) {
                    if(genreList.size() > 5)
                        genreList = genreList.subList(0, 4);
                    // validation is complete, append now
                    temp.append(INNER_JOIN).append("(SELECT DISTINCT movie_id FROM movie_genre WHERE genre_id IN (").append("SELECT genre_id FROM genre WHERE genre IN ('");
                    temp.append(String.join("','", genreList)).append("'))) AS genre_filter ON movie_filter.movie_id = genre_filter.movie_id");
                }

                return temp;
            }
            /*
             * -- This inner join will be appended in the final query string if the keywordList is not null or when the keywordList does not contain zero elements and contains less than 5 elements
             * -- Strip off to five elements if found guilty
             * INNER JOIN (SELECT DISTINCT movie_id FROM movie_keyword WHERE keyword_id IN (
             *         SELECT keyword_id FROM keyword WHERE keyword IN ('patriarch', 'mafia', 'lawyer'))) AS keyword_filter
             * ON movie_filter.movie_id = keyword_filter.movie_id;
            */
            private StringBuilder validateKeywordDataAndAppendString() {
                StringBuilder temp = new StringBuilder();

                List<String> keywordList = filterRequestDTO.keywordList();

                if(keywordList!=null && keywordList.size()!=0) {
                    if(keywordList.size() > 5)
                        keywordList = keywordList.subList(0, 4);
                    // validation is complete, append now
                    temp.append(INNER_JOIN).append("(SELECT DISTINCT movie_id FROM movie_keyword WHERE keyword_id IN (").append("SELECT keyword_id FROM keyword WHERE keyword IN ('");
                    temp.append(String.join("','", keywordList)).append("'))) AS keyword_filter ON movie_filter.movie_id = keyword_filter.movie_id");
                }

                return temp;
            }

            /*
             * -- This inner join will be appended in the final query string if the castNameList is not null or when the castNameList does not contain zero elements and contains less than 5 elements
             * -- Strip off to five elements if found guilty
             * INNER JOIN (SELECT DISTINCT movie_id FROM cast WHERE name IN ('Al Pacino', 'Marlon Brando')) AS cast_filter
             * ON movie_filter.movie_id = cast_filter.movie_id
             */
            private StringBuilder validateCastTableDataAndAppendString() {

                StringBuilder temp = new StringBuilder();
                
                List<String> castNameList = filterRequestDTO.castNameList();

                if(castNameList!=null && castNameList.size()!=0) {
                    if(castNameList.size() > 5)
                        castNameList = castNameList.subList(0, 4);
                    // validation is complete, append now
                    temp.append(INNER_JOIN).append("(SELECT DISTINCT movie_id FROM cast WHERE name IN ('");
                    temp.append(String.join("','", castNameList)).append("')) AS cast_filter ON movie_filter.movie_id = cast_filter.movie_id");
                }

                return temp;
            }
        }

        // ensure the page size value is not above the maximum page size
        if(pageSize > maxPageSize)
            pageSize = maxPageSize;
        pageRequest = PageRequest.of(pageNumber-1, pageSize);

        new DataValidationAndQueryGeneration();

        String finalQueryString = finalQueryStringBuilder.toString();

        if(finalQueryString.length()!=0)
            return  filterRequestDao.getMovieInfoSnapshotListForFilterWithPage(pageRequest, finalQueryString)
                                    .stream()
                                    .map(MovieService::convertEntityToDto)
                                    .collect(Collectors.toList());
        else
            return null;
    }
}
