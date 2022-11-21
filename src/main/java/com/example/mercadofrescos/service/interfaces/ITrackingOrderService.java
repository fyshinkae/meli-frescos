package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.TrackingOrder;

import java.util.List;
import java.util.Optional;

public interface ITrackingOrderService {
    List<TrackingOrder> findAllByPurchaseOrder(Long userId);

    List<TrackingOrder> findAllUser();

    Optional<TrackingOrder> findUserById(Long id);
    TrackingOrder create(TrackingOrder trackingOrder);
    TrackingOrder update(Long id, TrackingOrder trackingOrder);
}
