package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import com.example.mercadofrescos.service.interfaces.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BatchStockService implements IBatchStockService {

    private final IBatchStockRepo repo;
    private final IProductService serviceProduct;

    @Override
    public List<BatchStock> convertToListBatchStock(List<BatchStockDTO> batchesDTO, InboundOrder inboundOrder) {
        List<BatchStock> batches = new ArrayList<>();

        for(BatchStockDTO batch : batchesDTO) {
            Product product = serviceProduct.findById(batch.getProductId());
            batches.add(BatchStockDTO.convertToModelObject(batch, product, inboundOrder));
        }

        return batches;
    }

}
