package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBatchStockRepo extends JpaRepository<BatchStock, Long> {
    @Query("SELECT b FROM BatchStock b WHERE b.product.category = :category")
    List<BatchStock> getBatchStocksByCategory(Category category);
}
