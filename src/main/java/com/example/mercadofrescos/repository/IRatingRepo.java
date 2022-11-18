package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRatingRepo extends JpaRepository<Rating, CustomerProductId> {
}
