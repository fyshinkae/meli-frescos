package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.TrackingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITrakingOrderRepo extends JpaRepository<TrackingOrder, Long> {
    // Derived Query JPA
    List<TrackingOrder> findAllByPurchaseOrder_customer_id(Long purchaseOrder_customer_id);
}
