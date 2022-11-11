package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.*;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {


    private final IPurchaseOrderService service;
    @PostMapping
    public ResponseEntity<PurchasePriceDTO> createNewOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrder) {
        PurchasePriceDTO response = service.getCartAmount(PurchaseOrderRequestDTO.convert(purchaseOrder));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retorna uma lista de produtos pelo id da Ordem de compra passado pelo parâmetro
     * @author Ma, Theus, Giovanna
     * @param id da Ordem de compra
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getOrderById(@PathVariable Long id) {
        List<PurchaseItemResponseDTO> order = service.getPurchaseOrderById(id);
        return ResponseEntity.ok(order);
    }

}
