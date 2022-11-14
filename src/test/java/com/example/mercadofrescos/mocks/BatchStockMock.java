package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class BatchStockMock {
    public static BatchStock BachStockTest() {
        BatchStock batch = new BatchStock();

        batch.setId(1L);
        batch.setProduct(ProductMock.productTest());
        // batch.setInboundOrder(InboundOrderMock.InboundOrderTest());
        batch.setCurrentTemperature(18.0F);
        batch.setManufacturingDate(LocalDate.now());
        batch.setManufacturingTime(LocalDateTime.now());
        batch.setDueDate(LocalDate.parse("2023-10-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        batch.setProductQuantity(10);
        batch.setVolume(10.5F);
        return batch;
    }
}
