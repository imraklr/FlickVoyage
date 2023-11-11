package com.flickvoyage.dataretrievalservice.services.filter.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.flickvoyage.dataretrievalservice.dto.CastInfoDTO;
import com.flickvoyage.dataretrievalservice.entity.Cast;
import com.flickvoyage.dataretrievalservice.entity.Department;
import com.flickvoyage.dataretrievalservice.repository.dao.CastDao;
import com.flickvoyage.dataretrievalservice.services.filter.CastService;

@Service
public class CastServiceImpl implements CastService {
    private CastDao castDao;

    public CastServiceImpl(CastDao castDao) {
        this.castDao = castDao;
    }

    @Override
    public List<CastInfoDTO> getCastInfoByMovieId(int movieId) {
        return castDao.getCastInfoByMovieId(movieId)
        .stream()
        .map(this::convertEntityToDto)
        .collect(Collectors.toList());
    }

    /*
     * Cast Enitity to DTO conversion.
     * Note that we do not need to convert the incoming DTO to entity since in the end user service, we 
     * are not concerned with the client sending a complete Movie class details like genreList, keywordList etc.
     * It makes no sense to send information such as keywords of a movie from client side.
     * 
     * The following method getBasicMovieInfoDtoList() just returns a collection of Movie object transformed to a DTO.
    */
    private CastInfoDTO convertEntityToDto(Cast cast) {
        return new CastInfoDTO(
            cast.getName(), cast.getCharacter(), 
            cast.getDepartmentList().stream().map(Department::getDepartmentName).collect(Collectors.toList())
        );
    }
}
