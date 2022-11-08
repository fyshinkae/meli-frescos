package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepo extends JpaRepository<Product, Long> {
}
