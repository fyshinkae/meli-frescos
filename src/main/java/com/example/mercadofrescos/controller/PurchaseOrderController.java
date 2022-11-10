package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {


    private final IPurchaseOrderService service;
    @PostMapping()
    public ResponseEntity<PurchasePriceDTO> createNewOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrder) {
        PurchasePriceDTO response = service.getCartAmount(purchaseOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
