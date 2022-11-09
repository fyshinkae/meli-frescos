package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.service.InboundOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    @Autowired
    private InboundOrderService service;

    // GIOVANA FAZER JAVADOC
    @PostMapping
    public ResponseEntity<InboundOrderResponseDTO> save(@Valid @RequestBody InsertBatchRequestDTO inboundOrderRequestDTO) {
        Long warehouseId = inboundOrderRequestDTO.getInboundOrder().getWarehouseCode();
        InboundOrder inboundOrderRequest = InsertBatchRequestDTO.convert(inboundOrderRequestDTO);

        InboundOrderResponseDTO data = service.save(inboundOrderRequest, warehouseId);

        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
