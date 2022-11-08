package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDTO {
    private LocalDate orderDate;
    private Long sectionCode, orderNumber, warehouseCode;
    private List<BatchStockDTO> batchStock;
    public InboundOrderRequestDTO(InboundOrder inboundOrderParam) {
        Section section = inboundOrderParam.getSection();

        this.orderNumber = inboundOrderParam.getId();
        this.orderDate = inboundOrderParam.getOrderDate();
        this.sectionCode = section.getId();
        this.warehouseCode = section.getWarehouse().getId();

        List<BatchStockDTO> batches = new ArrayList<>();

        for(BatchStock batch: inboundOrderParam.getBatches()){
            batches.add(new BatchStockDTO(batch));
        }

        this.batchStock = batches;
    }
}
