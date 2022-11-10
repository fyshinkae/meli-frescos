package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseItemRepo extends JpaRepository<PurchaseItem, Long> {
}
