package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);

}
