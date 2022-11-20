package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.rating.RatingByUserDTO;
import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/rating")
@RequiredArgsConstructor
public class RatingController {

    private final IRatingService service;

    /**
     * Cria um Rating de um produto no banco de dados
     * @author Gabriel
     * @param request Json a ser mandado pelo usuário
     * @return Rating salvo no banco de dados e HTTP Status
     */
    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO request){
        RatingDTO response = this.service.createRating(RatingDTO.convert(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtém a lista de todos os ratings de um usuário
     * @author Gabriel
     * @param customerId
     * @return Uma lista de Ratings do customerId e HTTP Status
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<RatingByUserDTO> getRatingByUser(@PathVariable Long customerId){
        RatingByUserDTO response = this.service.getRatingByUser(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
