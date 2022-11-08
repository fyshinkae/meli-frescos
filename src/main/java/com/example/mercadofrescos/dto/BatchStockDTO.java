package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDTO {
    private Long batchNumber, productId;
    private Float currentTemperature, volume;
    private Integer productQuantity;
    private LocalDateTime manufacturingTime;
    private LocalDate manufacturingDate, dueTime;
    private BigDecimal price;

    /**
     * Converte um modelo do BatchStock para um DTO de BatchRequest
     * @author Gabriel
     * @param batchStock um objeto do modelo BatchStock a ser convertido
     */
    public BatchStockDTO(BatchStock batchStock){
        Product product = batchStock.getProduct();

        this.productId = product.getId();
        this.price = product.getPrice();

        this.batchNumber = batchStock.getId();
        this.currentTemperature = batchStock.getCurrentTemperature();
        this.volume = batchStock.getVolume();
        this.productQuantity = batchStock.getProductQuantity();
        this.manufacturingDate = batchStock.getManufacturingDate();
        this.manufacturingTime = batchStock.getManufacturingTime();
        this.dueTime = batchStock.getDueDate();
    }
}
