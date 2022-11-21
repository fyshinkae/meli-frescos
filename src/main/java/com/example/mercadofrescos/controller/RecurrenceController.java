package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.model.RecurrenceOrder;
import com.example.mercadofrescos.service.interfaces.IRecurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products")
@RequiredArgsConstructor
public class RecurrenceController {

    private final IRecurrenceService service;

    @PostMapping("/orders/recurrence")
    public ResponseEntity<RecurrenceResponseDTO> createRecurrenceFromOrder(@RequestBody RecurrenceOrderDTO recurrenceOrder) {
        RecurrenceResponseDTO response = service.createRecurrence(recurrenceOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
