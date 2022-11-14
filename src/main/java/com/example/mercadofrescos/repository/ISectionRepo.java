package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ISectionRepo extends JpaRepository<Section, Long> {
    @Query(value = "select s.id from warehouse as w\n" +
            "inner join section as s on s.warehouse_id = w.id\n" +
            "where s.id = ?2 and w.id = ?1", nativeQuery = true)
    Optional<Long> findSectionByWarehouseId(Long wirehouseId, Long sectionId);
}
