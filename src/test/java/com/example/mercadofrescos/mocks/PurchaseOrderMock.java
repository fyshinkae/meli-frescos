package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.StatusOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderMock {
    public static PurchaseOrder purchaseOrderTest() {
        User costumer = new User();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        PurchaseItem purchaseItemMock = PurchaseItemMock.puchaseItemTest();
        List<PurchaseItem> purchaseItemList = new ArrayList<>();

        purchaseItemList.add(purchaseItemMock);

        purchaseOrder.setId(1L);
        purchaseOrder.setCustomer(costumer);
        purchaseOrder.setStatusOrder(StatusOrder.ABERTO);
        purchaseOrder.setUpdatedAt(LocalDateTime.parse("2022-11-21 12:42:14", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        purchaseOrder.setDate(LocalDate.parse("2018-01-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        purchaseOrder.setItemList(purchaseItemList);

        return purchaseOrder;
    }
}
