package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Section;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDTO {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @NotNull
    @Min(0)
    private Long sectionCode, orderNumber, warehouseCode;

    @NotEmpty
    private List<BatchStockDTO> batchStock;

    /**
     * Converte um modelo de InboundOrder para um DTO de InboundOrder
     * @author Gabriel
     * @param inboundOrderParam um objeto do modelo InboundOrder a ser convertido
     */
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
