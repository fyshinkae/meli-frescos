package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRatingRepo extends JpaRepository<Rating, CustomerProductId> {
    @Query("SELECT r FROM Rating r WHERE r.id.customerId = :customerId ORDER BY r.createdAt DESC")
    List<Rating> findByCustomerId(Long customerId);
}
