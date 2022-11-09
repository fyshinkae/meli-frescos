package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.service.ProductService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    @Autowired
    private IProductService service;

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

    @GetMapping("/list/{category}")
    public ResponseEntity<List<ProductDTO>> getAllByCategory(@PathVariable Category category) {
        List<ProductDTO> filterByCategory = service.findByCategory(category);
        return new ResponseEntity<>(filterByCategory, HttpStatus.OK);
    }


}
