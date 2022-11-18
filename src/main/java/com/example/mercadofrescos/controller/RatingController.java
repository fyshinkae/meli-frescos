package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.service.interfaces.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/rating")
@RequiredArgsConstructor
public class RatingController {

    private final IRatingService service;

    @PostMapping
    public ResponseEntity<RatingDTO> createRating(@RequestBody RatingDTO request){
        RatingDTO response = this.service.createRating(RatingDTO.convert(request));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
