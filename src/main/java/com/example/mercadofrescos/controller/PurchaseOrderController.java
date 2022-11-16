package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.*;
import com.example.mercadofrescos.model.enums.OrderBy;
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

    // todo: JAVADOC
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

    /**
     * Ordena pela data de vencimento
     * @author Ma, Giovanna e Gabriel
     * @param sectionId e 'days'
     * @return retorna uma lista de 'batchStocks'
     */
    @GetMapping("/due-date")
    public ResponseEntity<BatchStockResponseDTO> getBatchStockOrderByDueDate(@RequestParam Integer days, @RequestParam Long sectionId) {
        BatchStockResponseDTO batchStock = serviceBatchStock.getBatchStockOrderByDueDate(days, sectionId);
        return ResponseEntity.ok(batchStock);
    }

    /**
     * Retorna uma lista de lotes no prazo de validade solicitada com uma determinada categoria de produto de forma crescente
     * @author Ma, Gabriel e Giovanna
     * @param days, category e orderBy
     * @return retorna uma lista de 'batchStocks'
     */
    @GetMapping("/due-date/list")
    public ResponseEntity<BatchStockResponseDTO> getBatchStockOrderByDueDateAndCategory(
            @RequestParam Integer days,
            @RequestParam String category,
            @RequestParam(required=false) OrderBy orderBy
    ) {
        BatchStockResponseDTO batchStock = serviceBatchStock.getBatchStockOrderByDueDateAndCategory(days, category, orderBy);
        return ResponseEntity.ok(batchStock);
    }

}
