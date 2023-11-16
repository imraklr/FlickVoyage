package com.flickvoyage.dataretrievalservice.controller;

import java.util.List;

// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flickvoyage.dataretrievalservice.dto.FilterRequestDTO;
import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;
import com.flickvoyage.dataretrievalservice.services.filter.FilterRequestService;

/**
 * NOTE: Do not put @CrossOrigin here as it will put its own header in ther request made through the API gateway
 * @author Rakesh Kumar
 */
@RestController
@RequestMapping("/")
public class DataRetrievalServiceController {
    private FilterRequestService filterRequestService;

    public DataRetrievalServiceController(FilterRequestService filterRequestService) {
        this.filterRequestService = filterRequestService;
    }

    @PostMapping("/filter")
    public List<MovieInfoSnapshotDTO> getMovieForAppliedFilter(
        @RequestParam(name = "pageNumber", defaultValue = "1", required = false) int pageNumber,
        @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
        @RequestBody FilterRequestDTO filterRequestDTO
    ) {
        return filterRequestService.getMovieInfoSnapshotListForFilter(filterRequestDTO, pageNumber, pageSize);
    }
}
