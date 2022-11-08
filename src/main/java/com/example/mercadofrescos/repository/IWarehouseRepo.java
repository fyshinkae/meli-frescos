package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWarehouseRepo extends JpaRepository<Warehouse, Long> {
}
