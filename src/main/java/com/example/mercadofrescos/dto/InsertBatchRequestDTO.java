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

    public InsertBatchRequestDTO(InboundOrder inboundOrderParam){
        this.inboundOrder = new InboundOrderRequestDTO(inboundOrderParam);
    }
}
