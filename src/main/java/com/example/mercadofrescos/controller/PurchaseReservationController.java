package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.service.interfaces.IPurchaseReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase/reservation")
@RequiredArgsConstructor
public class PurchaseReservationController {

    private final IPurchaseReservationService service;

    @PostMapping
    public ResponseEntity<PurchaseReservationResponseDTO> createReservation(@RequestBody PurchaseOrderRequestDTO purchase) {
        PurchaseReservationResponseDTO purchaseResponse = service.createReservation(PurchaseOrderRequestDTO.convertToReservation(purchase));

        return new ResponseEntity<>(purchaseResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseRequestDTO>> findAll() {
        List<PurchaseRequestDTO> purchaseOrders = service.findAll();

        return new ResponseEntity<>(purchaseOrders, HttpStatus.OK);
    }
}
