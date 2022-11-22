package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.tracking.TrackingOrderDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.model.TrackingOrder;

import java.util.List;
import java.util.Optional;

public interface ITrackingOrderService {
    TrackingOrderResponseDTO create(TrackingOrderRequestDTO trackingOrder);
    TrackingOrderResponseDTO update(Long id, TrackingOrder trackingOrder);
}
