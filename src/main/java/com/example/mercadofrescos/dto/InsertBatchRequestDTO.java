package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertBatchRequestDTO {
    private InboundOrderRequestDTO inboundOrder;

    /**
     * Converte o modelo InboundOrder para um DTO de Request de InsertBatch
     * @author Gabriel
     * @param inboundOrderParam um objeto do modelo InboundOrder para ser convertido
     */
    public InsertBatchRequestDTO(InboundOrder inboundOrderParam){
        this.inboundOrder = new InboundOrderRequestDTO(inboundOrderParam);
    }
}
