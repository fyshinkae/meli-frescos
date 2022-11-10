package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.dto.InboundOrderResponseDTO;
import com.example.mercadofrescos.dto.InsertBatchRequestDTO;
import com.example.mercadofrescos.model.InboundOrder;

import java.util.List;

public interface IInboundOrderService {
    InboundOrderResponseDTO save(InboundOrder request, Long warehouseId);
    BatchStockDTO update(InsertBatchRequestDTO request);
}
