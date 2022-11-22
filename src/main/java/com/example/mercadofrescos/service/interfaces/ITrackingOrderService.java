package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.model.TrackingOrder;

import java.util.List;

public interface ITrackingOrderService {
    TrackingOrderResponseDTO create(TrackingOrderRequestDTO trackingOrder);
    TrackingOrder findTrackingOrderById(Long id);
    List<TrackingOrder> findAllTrackingOrder();
}
