package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.repository.IProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private IProductRepo repo;

    @Test
    @DisplayName("Return all registered products")
    void newReturnProductList() {
        Product product1 = new Product();
        product1.setId(ProductMock.productTest().getId());
        product1.setSeller(ProductMock.productTest().getSeller());
        product1.setBatches(ProductMock.productTest().getBatches());
        product1.setName(ProductMock.productTest().getName());
        product1.setPrice(ProductMock.productTest().getPrice());
        product1.setCategory(ProductMock.productTest().getCategory());

        List<Product> productList = Arrays.asList(product1);

        Mockito.when(repo.findAll()).thenReturn(productList);

        List<ProductResponseDTO> result = productService.findAllProducts();

        assertThat(result).isNotNull();
        assertThat(result).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Validate if save product")
    void saveProduct() {
        Product product = new Product();
        product.setId(ProductMock.productTest().getId());
        product.setSeller(ProductMock.productTest().getSeller());
        product.setBatches(ProductMock.productTest().getBatches());
        product.setName(ProductMock.productTest().getName());
        product.setPrice(ProductMock.productTest().getPrice());
        product.setCategory(ProductMock.productTest().getCategory());

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(product);

        Product result = productService.saveProduct(product);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal(100.0));
        assertThat(result.getCategory()).isEqualTo(Category.FRESH);
    }

    @Test
    @DisplayName("Validate if update product")
    void updateProduct() {
        Product product = new Product();
        product.setId(ProductMock.productTest().getId());
        product.setSeller(ProductMock.productTest().getSeller());
        product.setBatches(ProductMock.productTest().getBatches());
        product.setName(ProductMock.productTest().getName());
        product.setPrice(ProductMock.productTest().getPrice());
        product.setCategory(ProductMock.productTest().getCategory());

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(product);

        Product result = productService.updatedProduct(product);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal(100.0));
        assertThat(result.getCategory()).isEqualTo(Category.FRESH);
    }

    @Test
    @DisplayName("findAllProducts not found product list")
    void productsNotFound() {
        Mockito.when(repo.findAll())
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> productService.findAllProducts())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Retorna uma exceção caso retorne uma arraylist vazia")
    void retornaUmaArrayListVazia() {
        Mockito.when(repo.findAll())
                .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> productService.findAllProducts())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findById not found product")
    void findProductNotFound() {
        Long id = 123456789L;
        Mockito.when(repo.findById(id))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> productService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }
}
