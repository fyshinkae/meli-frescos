package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    Product updatedProduct(Product product);
    List<ProductResponseDTO> findAllProducts();
    Product findById(Long id);
    List<ProductDTO> findByCategory(String category);
    ProductAgentResponseDTO findByIdForAgent(Long id);
    ProductAgentResponseDTO orderProductForAgent(ProductAgentResponseDTO product, String typeOrder);
}
