package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.PurchaseItemDTO;
import com.example.mercadofrescos.dto.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.enums.StatusOrder;

import java.util.List;

public interface IPurchaseOrderService {
    PurchasePriceDTO getCartAmount(PurchaseOrder purchaseOrder);
    List<PurchaseItemResponseDTO> getPurchaseOrderById(Long id);
    PurchaseOrderRequestDTO updateOrderStatus(StatusOrder updateStatus, Long id);
}
