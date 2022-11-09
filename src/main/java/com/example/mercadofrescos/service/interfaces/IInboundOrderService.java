package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;

import java.util.List;

public interface IInboundOrderService {
    List<BatchStockDTO> save(InsertBatchRequestDTO request);
    BatchStockDTO update(InsertBatchRequestDTO request);
}
