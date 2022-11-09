package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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

    @NotNull
    @Min(1)
    private Long batchNumber, productId;

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


    // MATHEUS ALVES FAZER JAVA DOC
    public static List<BatchStockDTO> convertToDTOList (List<BatchStock> batchStockList){
        List<BatchStockDTO> batchStockListDTO = new ArrayList<>();
        for(BatchStock batchStock : batchStockList) {
            batchStockListDTO.add(new BatchStockDTO(batchStock));
        }
        return batchStockListDTO;
    }

    public static BatchStock convertToModelObject(BatchStockDTO batchDTO, Product product, InboundOrder inboundOrder) {
        BatchStock response = new BatchStock();

        response.setId(batchDTO.getBatchNumber());
        response.setCurrentTemperature(batchDTO.getCurrentTemperature());
        response.setManufacturingDate(batchDTO.getManufacturingDate());
        response.setManufacturingTime(batchDTO.getManufacturingTime());
        response.setDueDate(batchDTO.getDueDate());
        response.setVolume(batchDTO.getVolume());
        response.setProductQuantity(batchDTO.getProductQuantity());

        response.setProduct(product);
        response.setInboundOrder(inboundOrder);

        return response;
    }

}
