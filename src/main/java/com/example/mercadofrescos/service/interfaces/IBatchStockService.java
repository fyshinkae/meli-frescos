package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;

public interface IBatchStockService {
    BatchStockDTO save(InsertBatchRequestDTO request);
    BatchStockDTO update(InsertBatchRequestDTO request);
}
