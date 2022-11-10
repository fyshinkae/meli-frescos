package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.PurchaseItem;

import java.util.List;

public interface IPurchaseItemService {
    List<PurchaseItem> savePurchaseItemList(List<PurchaseItem> purchaseItems);
}
