package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.*;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.service.interfaces.IOnSaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/orders-on-sale")
@RequiredArgsConstructor
public class OnSaleOrderController {

    private final IOnSaleOrderService service;

    /**
     * Cadastra um lote de produtos que esteja próximo da data de vencimento, com desconto progressivo
     * @author Ma
     * @param onSaleOrder do lote
     * @return retorna a soma do lote com o desconto aplicado
     */
    @PostMapping
    public ResponseEntity<OnSalePriceDTO> newOrderOnSale(@RequestBody PurchaseOrderRequestDTO onSaleOrder) {
        OnSalePriceDTO response = service.getCartOnSale(PurchaseOrderRequestDTO.convert(onSaleOrder));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retorna uma lista de produtos pelo "id" da ordem de compra promocional passada pelo parâmetro
     * @author Ma
     * @param id da Ordem de compra
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getOrderOnSaleById(@PathVariable Long id) {
        List<PurchaseItemResponseDTO> order = service.getOnSaleOrderById(id);

        return ResponseEntity.ok(order);
    }

    /**
     * Muda o status da ordem promocional
     * @author Ma
     * @param status da Ordem
     */
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderRequestDTO> updateOnSaleOrderStatus(@RequestBody StatusOrderDTO status, @PathVariable Long id) {
        StatusOrder onSaleOrder = status.getOrderStatus();
        PurchaseOrderRequestDTO orderUpdated = service.updateOnSaleOrderStatus(onSaleOrder, id);

        return ResponseEntity.ok(orderUpdated);
    }
}
