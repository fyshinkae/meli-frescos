package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.rating.*;
import com.example.mercadofrescos.model.enums.Reputation;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<RatingDTO> createRating(@Valid @RequestBody RatingDTO request){
        RatingDTO response = this.service.createRating(RatingDTO.convert(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Obtém a lista de todos as avaliações dos compradores
     * @author Gabriel
     * @return Uma lista de avaliações de todos os usuários e HTTP Status
     */
    @GetMapping("/customers")
    public ResponseEntity<List<RatingByUserDTO>> getRatingByUsers(){
        List<RatingByUserDTO> response = this.service.getRatingByUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
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
     * Obtém uma avaliação de um produto específico dado por um usuário
     * @param customerId Id do comprador
     * @param productId Id do produto
     * @return Uma avaliação do customerId de um productId e HTTP Status
     */
    @GetMapping("/customers/{customerId}/products/{productId}")
    public ResponseEntity<RatingByUserDTO> getRatingByUserAndProduct(
            @PathVariable Long customerId,
            @PathVariable Long productId){
        RatingByUserDTO response = this.service.getRatingByUserAndProduct(customerId, productId);
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

    /**
     * Obtém a lista dos vendedores com informações relacionadas a avaliações
     * @param reputation filtra os vendedores pela reputação
     * @return Uma lista de vendedores com informações de avaliações e HTTP Status
     */
    @GetMapping("/sellers")
    public ResponseEntity<List<RatingBySellerDTO>> getRatingBySeller(
            @RequestParam(required = false) Reputation reputation
            ){
        List<RatingBySellerDTO> response = this.service.getRatingBySeller(reputation);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
