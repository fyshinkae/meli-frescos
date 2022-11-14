package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import org.assertj.core.util.Arrays;

import java.util.ArrayList;
import java.util.List;

public class PurchaseItemMock {
    public static List<PurchaseItem> puchaseItemTest() {
        PurchaseItem purchaseItem1 = new PurchaseItem();
        PurchaseItem purchaseItem2 = new PurchaseItem();
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseItem1.setId(1L);
        purchaseItem1.setProductId(ProductMock.productTest());
        purchaseItem1.setPurchaseOrderId(purchaseOrder);
        purchaseItem1.setProductQuantity(10);

        purchaseItem2.setId(2L);
        purchaseItem2.setProductId(ProductMock.productTest());
        purchaseItem2.setPurchaseOrderId(purchaseOrder);
        purchaseItem2.setProductQuantity(20);

        return new ArrayList<>(){
            {
                add(purchaseItem1);
                add(purchaseItem2);
            }
        };
    }
}
