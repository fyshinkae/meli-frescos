package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {
}
