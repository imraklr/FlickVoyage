package com.flickvoyage.dataretrievalservice.services.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;

import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;
import com.flickvoyage.dataretrievalservice.entity.Genre;
import com.flickvoyage.dataretrievalservice.entity.Movie;

/**
 * @implNote Here the convention for method name is [action, category/object name, options/conditions, names, parameters]. For example 
 * "adult" represents category, "Choice" represents option/condition which represents a boolean choice of true/false, 
 * "Movies" represents table name and "InRange" represents the parameters. These are not a stricter convention.
 * 
 * @author Rakesh Kumar
 */
public interface MovieService {

    /*
     * Exposed utility method for mapping a Movie object to a MovieInfoSnapshotDTO.
     * Movie Enitity to DTO conversion.
     * Note that we do not need to convert the incoming DTO to entity since in the end user service, we 
     * are not concerned with the client sending a complete Movie class details like genreList, keywordList etc.
     * It makes no sense to send information such as keywords of a movie from client side.
     * 
     * The following method getBasicMovieInfoDtoList() just returns a collection of Movie object transformed to a DTO.
    */
    static MovieInfoSnapshotDTO convertEntityToDto(Movie movie) {
        return new MovieInfoSnapshotDTO(
            movie.getMovieId(), movie.getTitle(), movie.getOverview(), movie.getPosterLink(), movie.getLanguage(), 
            movie.isAdult(), movie.getPopularityScore(), movie.getAverageVote(), movie.getTotalVoteCount(), 
            movie.getReleaseYear(), movie.getGenresList().stream().map(Genre::getGenre).collect(Collectors.toList())
        );
    }

    // Initial Serving Methods
    List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageNumber); // default and new page serving
    List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageSize, Byte dummy); // serving with same page but new size
    List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageNumber, int pageSize); // serving with new page and new size

    // Methods that serve the filter criterias
    List<String> getAllUniqueLanguages();
    List<String> getAllGenres();
    Integer releaseYearLowerBound();
    Integer releaseYearUpperBound();
    Integer popularityScoreLowerBound();
    Integer popularityScoreUpperBound();
    List<String> getRandomKeywords();
    List<String> getRandomCasts();

    // PUT methods to reset the page size
    boolean resetPageSize(int to);



    // method that returns data with a limit and offset [possible use of this method is to serve the admin a requested set of data]
    List<Movie> getMoviesInRange(PageRequest pageRequest);
}
