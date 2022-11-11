package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseOrder;

public interface IPurchaseOrderService {
    PurchasePriceDTO getCartAmount(PurchaseOrder purchaseOrder);
}
