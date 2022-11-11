package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BatchStockMock {
    public static BatchStock BachStockTest() {
        BatchStock batch = new BatchStock();

        batch.setId(1L);
        batch.setProduct(ProductMock.productTest());
        batch.setCurrentTemperature(18.0F);
        batch.setManufacturingDate(LocalDate.now());
        batch.setManufacturingTime(LocalDateTime.now());
        batch.setDueDate(LocalDate.now());
        batch.setProductQuantity(10);
        batch.setVolume(10.5F);
        return batch;
    }
}
