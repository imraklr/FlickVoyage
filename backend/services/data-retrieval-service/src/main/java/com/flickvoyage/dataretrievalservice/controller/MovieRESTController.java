package com.flickvoyage.dataretrievalservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;
import com.flickvoyage.dataretrievalservice.services.filter.MovieService;

/**
 * NOTE: Do not put @CrossOrigin here as it will put its own header in ther request made through the API gateway
 * @author Rakesh Kumar
 */
@RestController
@RequestMapping("/movie")
public class MovieRESTController {
    private MovieService movieService;

    public MovieRESTController(MovieService movieService) {
        this.movieService = movieService;
    }


    /**
     * This method serves the singular request of the client for new page number specified by 
     * <code>pageNumber</code> AND/OR new page size specified by <code>pageSize</code>.
     * @param pageNumber
     * @param pageSize
     * @return List of Movie items
     */
    @GetMapping("")
    public List<MovieInfoSnapshotDTO> getMoviesForDisplay(
        @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return movieService.getMovieInfoSnapshotDTOsForPage(pageNumber, pageSize);
    }

    /**
     * This endpoint has to be accessed to change the page number, the page size remains intact
     * @param pageNumber
     * @return
     */
    @GetMapping("/page/{pN}")
    public List<MovieInfoSnapshotDTO> getMoviesForDisplay(
        @PathVariable(name = "pN") int pageNumber
    ) {
        return movieService.getMovieInfoSnapshotDTOsForPage(pageNumber);
    }

    /**
     * This endpoint has to be accessed to change the page request size. This change may affect how other endpoints work.
     * @param pageSize
     * @param dummy
     * @return List of Movie
     */
    @GetMapping("/pageSize/{pS}")
    public List<MovieInfoSnapshotDTO> getMoviesForDisplay(@PathVariable(name = "pS") int pageSize, Byte dummy) {
        return movieService.getMovieInfoSnapshotDTOsForPage(pageSize, dummy);
    }

    @PutMapping(value="pageSize/{to}")
    public ResponseEntity<Boolean> putMethodName(@PathVariable(name = "to") int to) {
        return ResponseEntity.ok(movieService.resetPageSize(to));
    }


    @GetMapping("/languages")
    public ResponseEntity<List<String>> getLanguages() {
        List<String> allLangs = movieService.getAllUniqueLanguages();

        if(allLangs!=null)
            return ResponseEntity.ok(allLangs);

        return null;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<String>> getGenres() {
        List<String> genres = movieService.getAllGenres();

        if(genres!=null)
            return ResponseEntity.ok(genres);

        return null;
    }

    @GetMapping("/releaseYearLowerBound")
    public ResponseEntity<Integer> getReleaseYearLowerBound() {
        Integer rYLB = movieService.releaseYearLowerBound();

        if(rYLB!=null)
            return ResponseEntity.ok(rYLB);
        
        return null;
    }

    @GetMapping("/releaseYearUpperBound")
    public ResponseEntity<Integer> getReleaseYearUpperBound() {
        Integer rYUB = movieService.releaseYearUpperBound();

        if(rYUB!=null)
            return ResponseEntity.ok(rYUB);
        
        return null;
    }

    @GetMapping("/popularityScoreLowerBound")
    public ResponseEntity<Integer> getPopularityScoreLowerBound() {
        Integer pSLB = movieService.popularityScoreLowerBound();

        if(pSLB!=null)
            return ResponseEntity.ok(pSLB);
        
        return null;
    }
    @GetMapping("/popularityScoreUpperBound")
    public ResponseEntity<Integer> getPopularityScoreUpperBound() {
        Integer pSUB = movieService.popularityScoreUpperBound();

        if(pSUB!=null)
            return ResponseEntity.ok(pSUB);
        
        return null;
    }

    @GetMapping("/randomKeywords")
    public ResponseEntity<List<String>> getRandomKeywords() {
        List<String> randKeys = movieService.getRandomKeywords();

        if(randKeys!=null)
            return ResponseEntity.ok(randKeys);
        
        return null;
    }

    @GetMapping("/randomCasts")
    public ResponseEntity<List<String>> getRandomCasts() {
        List<String> randCasts = movieService.getRandomCasts();

        if(randCasts!=null)
            return ResponseEntity.ok(randCasts);
        
        return null;
    }
}
