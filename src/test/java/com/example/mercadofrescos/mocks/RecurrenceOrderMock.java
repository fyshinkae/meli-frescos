package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.RecurrenceOrder;

public class RecurrenceOrderMock {
    public static RecurrenceOrder recurrenceOrderTest() {
        RecurrenceOrder recurrenceOrder = new RecurrenceOrder();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(PurchaseOrderMock.purchaseOrderTest().getId());
        purchaseOrder.setCustomer(PurchaseOrderMock.purchaseOrderTest().getCustomer());
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(PurchaseOrderMock.purchaseOrderTest().getDate());
        purchaseOrder.setItemList(PurchaseOrderMock.purchaseOrderTest().getItemList());

        recurrenceOrder.setId(1L);
        recurrenceOrder.setDayOfMonth(10L);
        recurrenceOrder.setPurchaseOrder(purchaseOrder);
        return recurrenceOrder;
    }
}
