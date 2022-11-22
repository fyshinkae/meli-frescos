package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.purchase.*;
import com.example.mercadofrescos.service.interfaces.IPurchaseReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<PurchaseReservationResponseDTO> createReservation(@Valid @RequestBody PurchaseReservationRequestDTO purchase) {
        PurchaseReservationResponseDTO purchaseResponse = service.createReservation(PurchaseReservationRequestDTO.convert(purchase));

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

    /**
     * Busca um pedido reservado
     * @author Theus
     * @param id do pedido
     * @return Retorna um objeto do modelo PurchaseOrder
     */
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseReservationResponseDTO> findById(@PathVariable Long id) {
        PurchaseReservationResponseDTO purchaseOrder = service.findById(id);

        return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
    }

    /**
     * Remove um pedido reservado
     * @author Theus
     * @param id do pedido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica a disponibilidade da reserva do pedido
     * @author Theus
     * @param id do pedido
     */
    @GetMapping("/availability/{id}")
    public ResponseEntity<CheckAvailabilityResponseDTO> verifyAvailability(@PathVariable Long id) {
        CheckAvailabilityResponseDTO data = service.verifyAvailability(id);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * Finaliza a reserva do pedido
     * @author Theus
     * @param id do pedido
     */
    @PostMapping("/finish/{id}")
    public ResponseEntity<PurchaseOrderRequestDTO> finishReservation(@PathVariable Long id) {
        PurchaseOrderRequestDTO data = service.finishReservation(id);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
