package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.enums.StatusShipping;

import java.time.LocalDateTime;

public class ShippingMock {
    public static Shipping shippingTest() {
        Shipping shipping = new Shipping();
        shipping.setId(1L);
        shipping.setStatusShipping(StatusShipping.PREPARING);
        shipping.setDescription("Your order has been received");
        shipping.setUpdateDate(LocalDateTime.now());

        return shipping;
    }
}
