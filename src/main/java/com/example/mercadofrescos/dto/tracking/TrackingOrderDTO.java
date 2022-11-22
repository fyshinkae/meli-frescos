package com.example.mercadofrescos.dto.tracking;

import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.Shipping;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrackingOrderDTO {
    private Long purchaseOrderId;
    private LocalDateTime trackingDate;
    private String receiver;
    private Address address;
    private Shipping shipping;
    private String description;
    private LocalDateTime shippingUpdatedAt;
}
