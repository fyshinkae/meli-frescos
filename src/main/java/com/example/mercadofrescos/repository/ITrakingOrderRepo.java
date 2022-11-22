package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.TrackingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrakingOrderRepo extends JpaRepository<TrackingOrder, Long> {
    @Query("SELECT t FROM TrackingOrder t WHERE t.purchaseOrder.id = :id")
    TrackingOrder findTrackingOrderByPurchaseOrderId(Long id);
}
