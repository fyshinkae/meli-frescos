package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.model.RecurrenceOrder;
import com.example.mercadofrescos.service.interfaces.IRecurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/orders/recurrence")
@RequiredArgsConstructor
public class RecurrenceController {

    private final IRecurrenceService service;

    /**
     * Retorna a compra recorrente criada
     * @author Anderson Alves
     * @param recurrenceOrder
     * @return retorna a 'recurrenceOrder' criada
     */
    @PostMapping("/{id}")
    public ResponseEntity<RecurrenceResponseDTO> createRecurrenceFromOrder(@RequestBody RecurrenceOrder recurrenceOrder, @PathVariable Long id) {
        recurrenceOrder.setId(id);
        RecurrenceResponseDTO response = service.createRecurrence(recurrenceOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Retorna uma lista de compras recorrentes
     * @author Anderson Alves
     * @return retorna uma lista de 'recurrenceOrder'
     */
    @GetMapping
    public ResponseEntity<List<RecurrenceOrderDTO>> getRecurrenceOrders() {
        List<RecurrenceOrderDTO> response = service.getAllRecurrences();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Edita uma compra recorrente especificada pelo ID
     * @author Anderson Alves
     * @param recurrenceOrder, id
     * @return retorna uma 'recurrenceOrder' atualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity updateRecurrenceOrder(@RequestBody RecurrenceOrder recurrenceOrder, @PathVariable Long id) {
        recurrenceOrder.setId(id);
        RecurrenceResponseDTO response = service.updateRecurrence(recurrenceOrder, id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deleta uma compra recorrente
     * @author Anderson Alves
     * @param id
     * @return retorna status 200
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteRecurrence(@PathVariable Long id) {
        service.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
