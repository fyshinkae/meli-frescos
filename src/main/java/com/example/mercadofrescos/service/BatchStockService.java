package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final IBatchStockRepo repo;

    @Override
    public BatchStockDTO save(InsertBatchRequestDTO request) {
        return null;
    }

    @Override
    public BatchStockDTO update(InsertBatchRequestDTO request) {
        return null;
    }
}
