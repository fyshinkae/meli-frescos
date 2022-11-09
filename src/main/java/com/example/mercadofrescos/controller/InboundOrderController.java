package com.example.mercadofrescos.controller;

import com.example.mercadofrescos.dto.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.service.InboundOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
@RequiredArgsConstructor
public class InboundOrderController {

    private final InboundOrderService service;

    /** Controller que valida e salva uma nova InboundOrder
     * @author Theus, Giovanna
     * @param inboundOrderRequestDTO objeto json com as informações do novo InboundOrder
     * @return Retorna um ResponseEntity como o novo InboundOrder criado
     */
    @PostMapping
    public ResponseEntity<InboundOrderResponseDTO> save(@Valid @RequestBody InsertBatchRequestDTO inboundOrderRequestDTO) {
        Long warehouseId = inboundOrderRequestDTO.getInboundOrder().getWarehouseCode();
        InboundOrder inboundOrderRequest = InsertBatchRequestDTO.convert(inboundOrderRequestDTO);

        InboundOrderResponseDTO data = service.save(inboundOrderRequest, warehouseId);

        return new ResponseEntity<>(data, HttpStatus.CREATED);
    }
}
