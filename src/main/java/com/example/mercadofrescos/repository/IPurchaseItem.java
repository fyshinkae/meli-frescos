package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseItem extends JpaRepository<PurchaseItem, Long> {
}
