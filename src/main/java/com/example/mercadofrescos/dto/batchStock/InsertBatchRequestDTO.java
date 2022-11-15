package com.example.mercadofrescos.dto.batchStock;

import com.example.mercadofrescos.dto.inboundOrder.InboundOrderRequestDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertBatchRequestDTO {
    @Valid
    private InboundOrderRequestDTO inboundOrder;

    /**
     * Converte um InsertBatchRequestDTO para um InboundOrder
     * @author Theus
     * @param inboundOrderRequest um objeto do modelo InsertBatchRequestDTO a ser convertido
     * @return um modelo InboundOrder
     */
    public static InboundOrder convert(InsertBatchRequestDTO inboundOrderRequest)  {
        InboundOrder inboundOrder = new InboundOrder();

        Section section = new Section();
        section.setId(inboundOrderRequest.getInboundOrder().getSectionCode());

        inboundOrder.setId(inboundOrderRequest.getInboundOrder().getOrderNumber());
        inboundOrder.setOrderDate(inboundOrderRequest.getInboundOrder().getOrderDate());
        inboundOrder.setSection(section);
        List<BatchStock> batches = inboundOrderRequest.getInboundOrder().getBatchStock().stream()
                .map(BatchStockDTO::convertToModelObject)
                .collect(Collectors.toList());
        inboundOrder.setBatches(batches);

        return inboundOrder;
    }
}
