package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrder extends JpaRepository<PurchaseOrder, Long> {
}
