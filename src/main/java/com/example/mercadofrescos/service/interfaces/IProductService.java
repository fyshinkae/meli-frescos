package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.product.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.product.ProductDTO;
import com.example.mercadofrescos.dto.product.ProductResponseDTO;
import com.example.mercadofrescos.model.Product;

import java.util.List;

public interface IProductService {
    Product saveProduct(Product product);
    List<ProductResponseDTO> findAllProducts();
    void validAllExists(List<Long> ids);
    Product findById(Long id);
    List<ProductDTO> findByCategory(String category);
    ProductAgentResponseDTO findByIdForAgent(Long id);
    ProductAgentResponseDTO orderProductForAgent(ProductAgentResponseDTO product, String typeOrder);
}
