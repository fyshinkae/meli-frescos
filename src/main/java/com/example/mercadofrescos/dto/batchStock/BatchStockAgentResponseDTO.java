package com.example.mercadofrescos.dto.batchStock;

import com.example.mercadofrescos.model.BatchStock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchStockAgentResponseDTO {

    private Long batchNumber;

    private Integer currentQuantity;

    private LocalDate dueDate;

    /**
     * Converte um BatchStock do modelo de banco para um DTO
     * @author Gabriel
     * @param batchStock BatchStock do modelo de banco a ser convertido
     */
    public BatchStockAgentResponseDTO(BatchStock batchStock){
        this.batchNumber = batchStock.getId();
        this.currentQuantity = batchStock.getProductQuantity();
        this.dueDate = batchStock.getDueDate();
    }

}
