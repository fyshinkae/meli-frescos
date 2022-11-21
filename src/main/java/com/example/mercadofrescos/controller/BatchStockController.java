package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.batchStock.BatchStockResponseDTO;
import com.example.mercadofrescos.model.enums.OrderBy;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products")
@RequiredArgsConstructor
public class BatchStockController {

    private final IBatchStockService serviceBatchStock;

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
