package com.example.mercadofrescos.dto.batchStock;

import com.example.mercadofrescos.model.BatchStock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BatchStockResponseDTO {
    List<BatchStockOrderByDueDateDTO> batchStock;

    public BatchStockResponseDTO(List<BatchStock> batchStock) {
        this.batchStock = new ArrayList<>();
        for (BatchStock batchStock1 : batchStock) {
            this.batchStock.add(new BatchStockOrderByDueDateDTO(batchStock1));
        }
    }
}
