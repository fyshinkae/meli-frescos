package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    Product updatedProduct(Product product);
    List<ProductResponseDTO> findAllProducts();
    Product findById(Long id);
}
