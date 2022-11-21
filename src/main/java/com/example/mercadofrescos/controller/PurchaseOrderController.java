package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchasePriceDTO;
import com.example.mercadofrescos.dto.purchase.StatusOrderDTO;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final IPurchaseOrderService service;

    /**
     * Cadastra um lote com o estoque de produtos que o compõe
     * @author Ma e Giovanna
     * @param purchaseOrder do lote
     */
    @PostMapping("/orders")
    public ResponseEntity<PurchasePriceDTO> createNewOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrder) {
        PurchasePriceDTO response = service.getCartAmount(PurchaseOrderRequestDTO.convert(purchaseOrder));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retorna uma lista de produtos pelo "id" da ordem de compra passado pelo parâmetro
     * @author Ma, Theus, Giovanna
     * @param id da Ordem de compra
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getOrderById(@PathVariable Long id) {
        List<PurchaseItemResponseDTO> order = service.getPurchaseOrderById(id);

        return ResponseEntity.ok(order);
    }

    /**
     * Muda o status
     * @author Ma, Gabriel, Giovanna
     * @param status da Ordem
     */
    @PutMapping("/orders/{id}")
    public ResponseEntity<PurchaseOrderRequestDTO> updateOrderStatus(@RequestBody StatusOrderDTO status, @PathVariable Long id) {
        StatusOrder purchaseOrder = status.getOrderStatus();
        PurchaseOrderRequestDTO orderUpdated = service.updateOrderStatus(purchaseOrder, id);

        return ResponseEntity.ok(orderUpdated);
    }



}
