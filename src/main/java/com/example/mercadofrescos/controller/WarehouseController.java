package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.ProductWarehousesDTO;
import com.example.mercadofrescos.service.interfaces.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class WarehouseController {

    @Autowired
    private IWarehouseService warehouseService;

    /**
     * Obtém um produto e sua lista de armazéns
     * @author Anderson e Gabriel
     * @param id id do produto a ser pesquisado
     * @return HTTP Status e um produto com sua lista de armazéns.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductWarehousesDTO> getWarehousesByProductId(@PathVariable Long id) {
        ProductWarehousesDTO response = warehouseService.getProductsQuantity(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
