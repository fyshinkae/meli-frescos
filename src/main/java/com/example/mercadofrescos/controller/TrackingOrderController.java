package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.service.interfaces.ITrackingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fresh-products/orders/tracking")
@RequiredArgsConstructor
public class TrackingOrderController {

    private final ITrackingOrderService service;

    @PostMapping("")
    public ResponseEntity<TrackingOrderResponseDTO> createTrackingOrder(@Valid @RequestBody TrackingOrderRequestDTO trackingOrder ) {
        TrackingOrderResponseDTO newTrackingOrder = service.create(trackingOrder);
        return new ResponseEntity<>(newTrackingOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingOrderResponseDTO> findTrackingOrderById(@PathVariable Long id) {
        TrackingOrder order = service.findTrackingOrderById(id);
        return new ResponseEntity<>(TrackingOrderResponseDTO.converter(order), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<TrackingOrderResponseDTO>> findAllTrackingOrder() {
        List<TrackingOrderResponseDTO> listResponse = new ArrayList<>();
        List<TrackingOrder> order = service.findAllTrackingOrder();

        for (TrackingOrder trackingOrder : order) {
            listResponse.add(TrackingOrderResponseDTO.converter(trackingOrder));
        }

        return new ResponseEntity<>(listResponse, HttpStatus.OK);
    }

}
