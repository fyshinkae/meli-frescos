package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.rating.RatingByProductDTO;
import com.example.mercadofrescos.dto.rating.RatingByUserDTO;
import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final IRatingService service;

    /**
     * Cria uma avaliação de um produto no banco de dados
     * @author Gabriel
     * @param request Json a ser mandado pelo usuário
     * @return Avaliação salva no banco de dados e HTTP Status
     */
    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO request){
        RatingDTO response = this.service.createRating(RatingDTO.convert(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtém a lista de todos as avaliações de um usuário
     * @author Gabriel
     * @param customerId Id do comprador
     * @return Uma lista de avaliações do customerId e HTTP Status
     */
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<RatingByUserDTO> getRatingByUser(@PathVariable Long customerId){
        RatingByUserDTO response = this.service.getRatingByUser(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Obtém a lista de todas as avaliações de um produto
     * @author Gabriel
     * @param productId Id do produto
     * @return Uma lista de avaliações do productId e HTTP Status
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<RatingByProductDTO> getRatingByProduct(@PathVariable Long productId){
        RatingByProductDTO response = this.service.getRatingByProduct(productId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
