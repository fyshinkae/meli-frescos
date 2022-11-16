package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
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

    /**
     * Cria um novo pedido de reserva
     * @author Theus
     * @param purchase um objeto do modelo PurchaseOrderRequestDTO
     * @return Retorna um objeto do modelo PurchaseReservationResponseDTO com os dados da reserva criada
     */
    @PostMapping
    public ResponseEntity<PurchaseReservationResponseDTO> createReservation(@RequestBody PurchaseOrderRequestDTO purchase) {
        PurchaseReservationResponseDTO purchaseResponse = service.createReservation(PurchaseOrderRequestDTO.convertToReservation(purchase));

        return new ResponseEntity<>(purchaseResponse, HttpStatus.CREATED);
    }

    /**
     * Busca todos os pedidos reservados
     * @author Theus
     * @return Retorna uma lista de objetos do modelo PurchaseRequestDTO
     */
    @GetMapping
    public ResponseEntity<List<PurchaseRequestDTO>> findAll() {
        List<PurchaseRequestDTO> purchaseOrders = service.findAll();

        return new ResponseEntity<>(purchaseOrders, HttpStatus.OK);
    }
}
