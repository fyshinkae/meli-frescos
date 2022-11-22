package com.example.mercadofrescos.mocks;


import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.TrackingOrder;

import java.time.LocalDateTime;


public class TrackingOrderMock {
    public static TrackingOrder trackingOrderTest () {
        TrackingOrder trackingOrder = new TrackingOrder();
        trackingOrder.setId(1L);
        trackingOrder.setPurchaseOrder(PurchaseOrderMock.purchaseOrderTest());
        trackingOrder.setTrackingDate(LocalDateTime.now());
        trackingOrder.setAddress(AddressMock.addressTest());
        trackingOrder.setShipping(ShippingMock.shippingTest());

        return trackingOrder;
    }
}
