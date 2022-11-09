package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService service;

    /**
     * Lista todos os produtos cadastrados na base de dados
     * @author Gabriel
     * @return HTTP Status e a lista de todos os produtos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<ProductResponseDTO> products = service.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
