package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface IWarehouseRepo extends JpaRepository<Warehouse, Long> {

    @Query("SELECT w \n" +
            "FROM Product p JOIN BatchStock b \n" +
            "ON p.id = b.product.id " +
            "JOIN InboundOrder i \n" +
            "ON i.id = b.inboundOrder.id " +
            "JOIN Section s on i.section.id = s.id\n" +
            "JOIN Warehouse w on s.warehouse.id = w.id\n" +
            "WHERE b.product.id = :productId\n" +
            "GROUP BY w.id")
    Set<Warehouse> getWarehousesByProductId(Long productId);
}
