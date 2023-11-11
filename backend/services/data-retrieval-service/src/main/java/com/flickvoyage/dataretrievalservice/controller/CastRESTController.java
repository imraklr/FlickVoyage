package com.flickvoyage.dataretrievalservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flickvoyage.dataretrievalservice.dto.CastInfoDTO;
import com.flickvoyage.dataretrievalservice.services.filter.CastService;

/**
 * NOTE: Do not put @CrossOrigin here as it will put its own header in ther request made through the API gateway
 * @author Rakesh Kumar
 */
@RestController
@RequestMapping("/cast")
public class CastRESTController {
    private CastService castService;

    public CastRESTController(CastService castService) {
        this.castService = castService;
    }


    @GetMapping("/{mId}")
    public List<CastInfoDTO> getAllCastOfMovie(@PathVariable(name = "mId") int movieId) {
        return castService.getCastInfoByMovieId(movieId);
    }
}
