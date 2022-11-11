package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseOrder;

import java.util.List;

public interface IPurchaseOrderService {
    PurchasePriceDTO getCartAmount(PurchaseOrder purchaseOrder);
    List<PurchaseItemResponseDTO> getPurchaseOrderById(Long id);
}
