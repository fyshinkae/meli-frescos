package com.example.mercadofrescos.dto.inboundOrder;

import com.example.mercadofrescos.dto.batchStock.BatchStockDTO;
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

    /**
     * Converte um modelo do InboundOrder para um DTO de resposta
     * @author Matheus Alves
     * @param inBoundOrder um objeto do modelo InBoundOrder a ser convertido
     */
    public InboundOrderResponseDTO(InboundOrder inBoundOrder) {
        this.batchStock = BatchStockDTO.convertToDTOList(inBoundOrder.getBatches());
    }
}
