package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    List<ProductResponseDTO> findAll();
    Product findById(Long id);
    List<ProductDTO> findByCategory(String category);
    Category filterCategory(String word);
    ProductAgentResponseDTO findByIdForAgent(Long id);
    ProductAgentResponseDTO orderProductForAgent(ProductAgentResponseDTO product, String typeOrder);
}
