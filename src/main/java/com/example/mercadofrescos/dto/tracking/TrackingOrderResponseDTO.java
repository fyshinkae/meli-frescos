package com.example.mercadofrescos.dto.tracking;

import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.TrackingOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrackingOrderResponseDTO {
    private Long purchaseOrderId;
    private LocalDateTime trackingDate;
    private Long shippingId;
    private Address address;

    public static TrackingOrderResponseDTO converter(TrackingOrder trackingOrder) {
        TrackingOrderResponseDTO trackingOrderResponseDTO = new TrackingOrderResponseDTO();
        trackingOrderResponseDTO.setPurchaseOrderId(trackingOrder.getPurchaseOrder().getId());
        trackingOrderResponseDTO.setTrackingDate(trackingOrder.getTrackingDate());
        trackingOrderResponseDTO.setShippingId(trackingOrder.getShipping().getId());
        trackingOrderResponseDTO.setAddress(trackingOrder.getAddress());
        return trackingOrderResponseDTO;
    }
}
