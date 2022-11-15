package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.inboundOrder.InboundOrderResponseDTO;
import com.example.mercadofrescos.model.InboundOrder;


public interface IInboundOrderService {
    InboundOrderResponseDTO update(InboundOrder request, Long warehouseId);
    InboundOrderResponseDTO save(InboundOrder request, Long warehouseId);
}
