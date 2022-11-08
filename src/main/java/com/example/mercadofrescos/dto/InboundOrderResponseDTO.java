package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderResponseDTO {

    private List<BatchStockDTO> batchStock;

    // MATHEUS ALVES FAZER JAVA DOC
    public InboundOrderResponseDTO(InboundOrder inBoundOrder) {
        this.batchStock = BatchStockDTO.convertToDTOList(inBoundOrder.getBatches());
    }
}
