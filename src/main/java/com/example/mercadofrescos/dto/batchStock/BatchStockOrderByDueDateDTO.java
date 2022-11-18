package com.example.mercadofrescos.dto.batchStock;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockOrderByDueDateDTO {

    private Long batchNumber;

    private Long productId;

    private Category productTypeId;

    private LocalDate dueDate;

    private Integer quantity;

    public BatchStockOrderByDueDateDTO(BatchStock batchStock) {
        this.batchNumber = batchStock.getId();
        this.productId = batchStock.getProduct().getId();
        this.productTypeId = batchStock.getProduct().getCategory();
        this.dueDate = batchStock.getDueDate();
        this.quantity = batchStock.getProductQuantity();
    }
}
