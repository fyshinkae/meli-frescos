package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.InboundOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertBatchRequestDTO {
    @Valid
    private InboundOrderRequestDTO inboundOrder;

    public static InboundOrder convert(InboundOrderRequestDTO InboundOrderRequest)  {
        InboundOrder inboundOrder = new InboundOrder();

        inboundOrder.setOrderDate(InboundOrderRequest.getOrderDate());

        return inboundOrder;
    }
}
