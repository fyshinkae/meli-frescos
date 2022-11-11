package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchStockDTO {
    private Long batchNumber;

    @NotNull
    @Min(1)
    private Long productId;

    @NotNull
    @Min(1)
    private Float currentTemperature, volume;

    @NotNull
    @Min(1)
    private Integer productQuantity;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingTime;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate manufacturingDate, dueDate;

    @NotNull
    @Min(1)
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
        this.dueDate = batchStock.getDueDate();
    }

    /**
     * Converte uma lista de modelo do BatchStock para uma lista de DTO de BatchRequest
     * @author Matheus Alves
     * @param batchStockList uma lista de objeto do modelo BatchStock a ser convertido
     */
    public static List<BatchStockDTO> convertToDTOList (List<BatchStock> batchStockList){
        List<BatchStockDTO> batchStockListDTO = new ArrayList<>();
        for(BatchStock batchStock : batchStockList) {
            batchStockListDTO.add(new BatchStockDTO(batchStock));
        }

        return batchStockListDTO;
    }

    /**
     * @author Gabriel
     * @param batchDTO
     * @return
     */
    public static BatchStock convertToModelObject(BatchStockDTO batchDTO) {
        BatchStock response = new BatchStock();
        Product product = new Product();

        product.setId(batchDTO.getProductId());
        response.setId(batchDTO.getBatchNumber());
        response.setProduct(product);
        response.setCurrentTemperature(batchDTO.getCurrentTemperature());
        response.setManufacturingDate(batchDTO.getManufacturingDate());
        response.setManufacturingTime(batchDTO.getManufacturingTime());
        response.setDueDate(batchDTO.getDueDate());
        response.setVolume(batchDTO.getVolume());
        response.setProductQuantity(batchDTO.getProductQuantity());

        return response;
    }

}
