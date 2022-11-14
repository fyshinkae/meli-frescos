package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.StatusOrder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderMock {
    public static PurchaseOrder purchaseOrderTest() {
        User costumer = new User();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<PurchaseItem> itemMockList = PurchaseItemMock.puchaseItemTest();
        
        purchaseOrder.setId(1L);
        purchaseOrder.setCustomer(costumer);
        purchaseOrder.setStatusOrder(StatusOrder.ABERTO);
        purchaseOrder.setDate(LocalDate.parse("2018-01-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        purchaseOrder.setItemList(itemMockList);

        return purchaseOrder;
    }
}
