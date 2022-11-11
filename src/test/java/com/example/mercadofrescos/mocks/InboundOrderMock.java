package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Arrays;

public class InboundOrderMock {
    public static InboundOrder InboundOrderTest () {
        InboundOrder inboundOrder = new InboundOrder();

        List<BatchStock> batches = Arrays.asList(BatchStockMock.BachStockTest());

        for (BatchStock batch : batches) {
            batch.setInboundOrder(inboundOrder);
        }

        inboundOrder.setId(1L);
        inboundOrder.setOrderDate(LocalDate.now());
        inboundOrder.setSection(SectionMock.SectionTest());
        inboundOrder.setBatches(batches);
        return inboundOrder;
    }
}