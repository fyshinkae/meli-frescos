package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.Section;

import java.util.List;
import java.util.Optional;

public interface IBatchStockService {
    BatchStockDTO save(InsertBatchRequestDTO request);
    BatchStockDTO update(InsertBatchRequestDTO request);

    List<BatchStock> validBatchStockList(InboundOrder inboundOrder);

}
