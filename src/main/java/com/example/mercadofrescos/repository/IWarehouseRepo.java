package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IWarehouseRepo extends JpaRepository<Warehouse, Long> {

    @Query(value = "SELECT Distinct warehouse.*\n" +
            "FROM (SELECT \n" +
            "product_id, batch_stock.id batch_stock_id, inbound_order_id, product_quantity\n" +
            "FROM product JOIN batch_stock \n" +
            "ON product.id = batch_stock.product_id) as product_batch\n" +
            "JOIN inbound_order \n" +
            "ON inbound_order.id = product_batch.inbound_order_id JOIN section on inbound_order.section_id = section.id\n" +
            "JOIN warehouse on section.warehouse_id = warehouse.id\n" +
            "WHERE product_batch.product_id = :productId", nativeQuery = true)
    List<Warehouse> getWarehousesByProductId(Long productId);
}
