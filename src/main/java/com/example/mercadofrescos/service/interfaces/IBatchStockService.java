package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;

import java.util.List;

public interface IBatchStockService {
    void saveBatchStockList(List<BatchStock> batches);
    List<BatchStock> validBatchStockList(InboundOrder inboundOrder);
    void verifyIfAllBatchStockExists(List<BatchStock> batches);
    BatchStock findById(Long id);
}
