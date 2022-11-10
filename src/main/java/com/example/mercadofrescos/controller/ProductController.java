package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.service.ProductService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Lista os produtos filtrados por categoria
     * @param category categoria dos produtos
     * @return lista de produtos filtrado e HTTP status
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAllByCategory(@RequestParam(required = false, name = "category") String category) {
        List<ProductDTO> filterByCategory = service.findByCategory(category);
        return new ResponseEntity<>(filterByCategory, HttpStatus.OK);
    }

}
