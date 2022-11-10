package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;

public interface IPurchaseOrderService {
    PurchasePriceDTO getCartAmount(PurchaseOrderRequestDTO purchaseItemList);
}
