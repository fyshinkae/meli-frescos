package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;

public class PurchaseItemMock {
    public static PurchaseItem puchaseItemTest() {
        PurchaseItem purchaseItem = new PurchaseItem();
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseItem.setId(1L);
        purchaseItem.setProductId(ProductMock.productTest());
        purchaseItem.setPurchaseOrderId(purchaseOrder);
        purchaseItem.setProductQuantity(10);

        return purchaseItem;
    }
}
