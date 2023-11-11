package com.flickvoyage.dataretrievalservice.services.filter.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;
import com.flickvoyage.dataretrievalservice.entity.Movie;
import com.flickvoyage.dataretrievalservice.repository.dao.MovieDao;
import com.flickvoyage.dataretrievalservice.services.filter.MovieService;

/**
 * @author Rakesh Kumar
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Value("${paging.initial.size}")
    private int initialPageSize;

    @Value("${paging.maxsize}")
    private int maxPageSize;

    @Value("${paging.minsize}")
    private int minPageSize;

    PageRequest pageRequest = null;

    private MovieDao movieDao;


    public MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageNumber) {
        if(pageNumber <= 0)
            return null;
        if(pageRequest == null)
            pageRequest = PageRequest.of(pageNumber-1, initialPageSize);
        else {
            // pageRequest = pageRequest.next();
            pageRequest = PageRequest.of(pageNumber-1, pageRequest.getPageSize());
        }
        
        return movieDao.getMoviesForPage(pageRequest)
                            .stream()
                            .map(MovieService::convertEntityToDto)
                            .collect(Collectors.toList());
    }
    
    @Override
    public List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageSize, Byte dummy) {
        if(pageSize <= 0)
            return null;
        else if(pageSize > maxPageSize)
            pageSize = maxPageSize;
        pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageSize);

        return movieDao.getMoviesForPage(pageRequest)
                            .stream()
                            .map(MovieService::convertEntityToDto)
                            .collect(Collectors.toList());
    }

    @Override
    public List<MovieInfoSnapshotDTO> getMovieInfoSnapshotDTOsForPage(int pageNumber, int pageSize) {
        if(pageNumber <= 0 || pageSize <= 0)
            return null;
        else if(pageSize > maxPageSize)
            pageSize = maxPageSize;
        // update the existing PageRequest
        pageRequest = PageRequest.of(pageNumber-1, pageSize);

        return movieDao.getMoviesForPage(pageRequest)
                            .stream()
                            .map(MovieService::convertEntityToDto)
                            .collect(Collectors.toList());
    }

    @Override
    public boolean resetPageSize(int to) {
        if(to > maxPageSize)
            return false;
        else if(to < minPageSize)
            return false;
        else {
            pageRequest = PageRequest.of(pageRequest.getPageNumber(), to);
            return true;
        }
    }

    // method for the admin
    @Override
    public List<Movie> getMoviesInRange(PageRequest adminPageRequest) {
        if(adminPageRequest.getPageNumber() < 0 || adminPageRequest.getPageSize() < 0) {
            // create a new with default size
            adminPageRequest = PageRequest.of(0, maxPageSize);
        }
        
        return movieDao.getMoviesForPage(adminPageRequest);
    }

    @Override
    public List<String> getAllUniqueLanguages() {
        return movieDao.getAllUniqueLanguages();
    }

    @Override
    public List<String> getAllGenres() {
        return movieDao.getAllGenres();
    }

    @Override
    public Integer releaseYearLowerBound() {
        return movieDao.releaseYearLowerBound();
    }

    @Override
    public Integer releaseYearUpperBound() {
        return movieDao.releaseYearUpperBound();
    }

    @Override
    public Integer popularityScoreLowerBound() {
        return movieDao.popularityScoreLowerBound();
    }

    @Override
    public Integer popularityScoreUpperBound() {
        return movieDao.popularityScoreUpperBound();
    }

    @Override
    public List<String> getRandomKeywords() {
        return movieDao.getRandomKeywords();
    }

    @Override
    public List<String> getRandomCasts() {
        return movieDao.getRandomCasts();
    }
}
