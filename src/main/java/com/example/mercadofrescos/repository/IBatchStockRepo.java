package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.BatchStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBatchStockRepo extends JpaRepository<BatchStock, Long> {
}
