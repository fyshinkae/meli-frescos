package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.enums.StatusOrder;

import java.util.List;

public interface IPurchaseOrderService {
    PurchaseOrder findById(Long id);
    PurchasePriceDTO getCartAmount(PurchaseOrder purchaseOrder);
    List<PurchaseItemResponseDTO> getPurchaseOrderById(Long id);
    PurchaseOrderRequestDTO updateOrderStatus(StatusOrder updateStatus, Long id);
}
