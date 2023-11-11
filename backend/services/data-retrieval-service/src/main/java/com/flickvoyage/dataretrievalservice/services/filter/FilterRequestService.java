package com.flickvoyage.dataretrievalservice.services.filter;

import java.util.List;

import com.flickvoyage.dataretrievalservice.dto.FilterRequestDTO;
import com.flickvoyage.dataretrievalservice.dto.MovieInfoSnapshotDTO;

/**
 * @author Rakesh Kumar
 */
public interface FilterRequestService {
    List<MovieInfoSnapshotDTO> getMovieInfoSnapshotListForFilter(FilterRequestDTO filterRequestDTO, int pageNumber, int pageSize);
}
