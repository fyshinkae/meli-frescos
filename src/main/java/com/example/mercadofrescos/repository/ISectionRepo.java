package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISectionRepo extends JpaRepository<Section, Long> {
}
