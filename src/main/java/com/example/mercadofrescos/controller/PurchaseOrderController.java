package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.*;
import com.example.mercadofrescos.dto.batchStock.BatchStockResponseDTO;
import com.example.mercadofrescos.dto.inboundOrder.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchasePriceDTO;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
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
    private final IBatchStockService serviceBatchStock;
    @PostMapping("/orders")
    public ResponseEntity<PurchasePriceDTO> createNewOrder(@RequestBody PurchaseOrderRequestDTO purchaseOrder) {
        PurchasePriceDTO response = service.getCartAmount(PurchaseOrderRequestDTO.convert(purchaseOrder));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/reservation")
    public ResponseEntity<InboundOrderResponseDTO> createReservation(@RequestBody PurchaseOrderRequestDTO purchase) {
        return null;
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

    /**
     * Ordena pela data de vencimento
     * @author Ma, Giovanna e Gabriel
     * @param id da 'section' e 'days'
     * @return retorna uma lista de 'batchStocks'
     */
    @GetMapping("/due-date/{days}/{sectionId}")
    public ResponseEntity<BatchStockResponseDTO> getBatchStockOrderByDueDate(@PathVariable Integer days, @PathVariable Long sectionId) {
        BatchStockResponseDTO batchStock = serviceBatchStock.getBatchStockOrderByDueDate(days, sectionId);
        return ResponseEntity.ok(batchStock);
    }

}
