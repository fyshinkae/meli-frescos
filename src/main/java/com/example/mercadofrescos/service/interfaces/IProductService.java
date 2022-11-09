package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    Product updatedProduct(Product product);
    List<Product> findAllProducts();
    Product findById(Long id);
}
