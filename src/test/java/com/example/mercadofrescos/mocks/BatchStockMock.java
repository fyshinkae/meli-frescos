package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;

public class BatchStockMock {

    public static BatchStock batchStockTest(){
        BatchStock batch = new BatchStock();

        batch.setId(1l);
        batch.setCurrentTemperature(10f);
        batch.setVolume(100f);
        batch.setProductQuantity(10);
        batch.setProduct(ProductMock.productTest());
        batch.setInboundOrder(new InboundOrder());
        batch.setDueDate(null);
        batch.setManufacturingTime(null);
        batch.setManufacturingDate(null);

        return batch;
    }
}
