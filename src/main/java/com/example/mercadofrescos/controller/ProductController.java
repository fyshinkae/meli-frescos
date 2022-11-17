package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
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
     * @return HTTP status e a lista de todos os produtos
     */
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAll(){
        List<ProductResponseDTO> products = service.findAll();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * Lista os produtos filtrados por categoria
     * @param category categoria dos produtos
     * @return lista de produtos filtrado e HTTP status
     */
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> findByCategory(
            @RequestParam(required = false, name = "category") String category
    ) {
        List<ProductDTO> filterByCategory = service.findByCategory(category);

        return new ResponseEntity<>(filterByCategory, HttpStatus.OK);
    }

    /**
     * Lista os dados do produto para o representante
     * @param productId id do produto
     * @param order ordenação opcional dos lotes do produto
     * @return um ProductAgentResponseDTO com os dados do produto
     */
    @GetMapping("/agent/list")
    public ResponseEntity<ProductAgentResponseDTO> getAllForAgent(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "order", required = false) String order
    ) {
        ProductAgentResponseDTO product = service.findByIdForAgent(productId);
        if (order != null) {
            product = service.orderProductForAgent(product, order);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
