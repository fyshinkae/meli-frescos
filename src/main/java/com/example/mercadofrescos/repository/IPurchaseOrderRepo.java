package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {
    @Query(value = "select p from PurchaseOrder as p where p.reservation = true")
    List<PurchaseOrder> findAllReservation();
}
