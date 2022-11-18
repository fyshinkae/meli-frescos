package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.batchStock.BatchStockResponseDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.enums.OrderBy;

import java.util.List;

public interface IBatchStockService {
    void saveBatchStockList(List<BatchStock> batches);
    List<BatchStock> validBatchStockList(InboundOrder inboundOrder);
    void verifyIfAllBatchStockExists(List<BatchStock> batches);
    BatchStock findById(Long id);
    BatchStockResponseDTO getBatchStockOrderByDueDate(Integer days, Long id);
    BatchStockResponseDTO getBatchStockOrderByDueDateAndCategory(Integer days, String category, OrderBy orderBy);
}
