package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.batchStock.BatchStockAgentResponseDTO;
import com.example.mercadofrescos.dto.product.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.product.ProductDTO;
import com.example.mercadofrescos.dto.product.ProductResponseDTO;
import com.example.mercadofrescos.exception.InvalidQueryParamException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.exception.ProductsListNotFoundException;
import com.example.mercadofrescos.mocks.BatchStockMock;
import com.example.mercadofrescos.mocks.InboundOrderMock;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.model.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private IProductRepo repo;

    private Product product;

    @BeforeEach
    void setup(){
        Product product = new Product();
        product.setId(ProductMock.productTest().getId());
        product.setSeller(ProductMock.productTest().getSeller());
        product.setBatches(ProductMock.productTest().getBatches());
        product.setName(ProductMock.productTest().getName());
        product.setPrice(ProductMock.productTest().getPrice());
        product.setCategory(ProductMock.productTest().getCategory());

        this.product = product;
    }

    @Test
    @DisplayName("Return all registered products")
    void newReturnProductList() {
        List<Product> productList = Arrays.asList(this.product);

        Mockito.when(repo.findAll()).thenReturn(productList);

        List<ProductResponseDTO> result = productService.findAll();

        assertThat(result).isNotNull();
        assertThat(result).size().isEqualTo(1);
    }

    @Test
    @DisplayName("Validate if save product")
    void saveProduct() {
        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(this.product);

        Product result = productService.saveProduct(this.product);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("100.0"));
        assertThat(result.getCategory()).isEqualTo(this.product.getCategory());
    }

    @Test
    @DisplayName("Validate if update product")
    void updateProduct() {
        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(this.product);

        Product result = productService.saveProduct(this.product);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("100.0"));
        assertThat(result.getCategory()).isEqualTo(this.product.getCategory());
    }

    @Test
    @DisplayName("findAllProducts not found product list")
    void productsNotFound() {
        Mockito.when(repo.findAll())
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> productService.findAll())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Retorna uma exceção caso retorne uma arraylist vazia")
    void retornaUmaArrayListVazia() {
        Mockito.when(repo.findAll())
                .thenReturn(new ArrayList<>());

        assertThatThrownBy(() -> productService.findAll())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("findById throws NotFoundException when product id is invalid")
    void findById_throwsNotFoundException_whenProductIdIsInvalid() {
        Long id = 123456789L;
        Mockito.when(repo.findById(id))
                .thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> productService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("FindByCategory use fresh category and return a list")
    void findByCategory_returnProductDTOList_whenCategoryFreshIsValid(){
        Category category = Category.FRESH;
        String categoryString = "FS";
        Product productFresh = ProductMock.getProductFresh();
        List<Product> products = new ArrayList<>();
        products.add(productFresh);

        Mockito.when(repo.findAllByCategory(category))
                .thenReturn(products);

        List<ProductDTO> response = productService.findByCategory(categoryString);
        ProductDTO firstProduct = response.get(0);

        assertThat(response).isNotNull();
        assertThat(firstProduct.getCategory()).isEqualTo(productFresh.getCategory());
        assertThat(firstProduct.getName()).isEqualTo(productFresh.getName());
        assertThat(firstProduct.getPrice()).isEqualTo(productFresh.getPrice());
        assertThat(firstProduct.getId()).isEqualTo(productFresh.getId());
    }

    @Test
    @DisplayName("FindByCategory use frozen category and return a list")
    void findByCategory_returnProductDTOList_whenCategoryFrozenIsValid(){
        Category category = Category.FROZEN;
        String categoryString = "FR";
        Product productFrozen = ProductMock.getProductFrozen();

        List<Product> products = new ArrayList<>();
        products.add(productFrozen);

        Mockito.when(repo.findAllByCategory(category))
                .thenReturn(products);

        List<ProductDTO> response = productService.findByCategory(categoryString);
        ProductDTO firstProduct = response.get(0);

        assertThat(response).isNotNull();
        assertThat(firstProduct.getCategory()).isEqualTo(productFrozen.getCategory());
        assertThat(firstProduct.getName()).isEqualTo(productFrozen.getName());
        assertThat(firstProduct.getPrice()).isEqualTo(productFrozen.getPrice());
        assertThat(firstProduct.getId()).isEqualTo(productFrozen.getId());
    }

    @Test
    @DisplayName("FindByCategory use refrigerated category and return a list")
    void findByCategory_returnProductDTOList_whenCategoryRefrigeratedIsValid(){
        Category category = Category.REFRIGERATED;
        String categoryString = "RF";
        Product productRefrigerated = ProductMock.getProductRefrigerated();

        List<Product> products = new ArrayList<>();
        products.add(productRefrigerated);

        Mockito.when(repo.findAllByCategory(category))
                .thenReturn(products);

        List<ProductDTO> response = productService.findByCategory(categoryString);
        ProductDTO firstProduct = response.get(0);

        assertThat(response).isNotNull();
        assertThat(firstProduct.getCategory()).isEqualTo(productRefrigerated.getCategory());
        assertThat(firstProduct.getName()).isEqualTo(productRefrigerated.getName());
        assertThat(firstProduct.getPrice()).isEqualTo(productRefrigerated.getPrice());
        assertThat(firstProduct.getId()).isEqualTo(productRefrigerated.getId());
    }

    @Test
    @DisplayName("FindByCategory throws InvalidQueryParamException when use a invalid category")
    void findByCategory_throwsInvalidQueryParamException(){
        Category category = Category.FRESH;
        String invalidCategory = "0";

        assertThatThrownBy(() -> productService.findByCategory(invalidCategory))
                .isInstanceOf(InvalidQueryParamException.class);
    }

    @Test
    @DisplayName("FindByCategory throws ProductsListNotFoundException when ProductList is empty")
    void findByCategory_throwsProductsListNotFoundException(){
        Category category = Category.REFRIGERATED;
        String categoryString = "RF";
        List<Product> products = new ArrayList<>();

        Mockito.when(repo.findAllByCategory(category))
                .thenReturn(products);

        assertThatThrownBy(() -> productService.findByCategory(categoryString))
                .isInstanceOf(ProductsListNotFoundException.class);
    }

    @Test
    @DisplayName("findByIdForAgent use a valid product id and return a ProductAgentResponseDTO")
    void findByIdForAgent_returnProductAgentResponseDTO_whenIdisValid(){
        Long validId = 1l;
        Product productFresh = ProductMock.getProductFresh();

        BatchStock batchStock = BatchStockMock.BachStockTest();
        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        batchStock.setInboundOrder(inboundOrder);
        Set<BatchStock> batches = new HashSet<>(){{
            add(batchStock);
        }};
        productFresh.setBatches(batches);


        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(productFresh));

        ProductAgentResponseDTO response = productService.findByIdForAgent(validId);

        assertThat(response).isNotNull();
        assertThat(response.getSection()).isNotNull();
        assertThat(response.getProductId()).isEqualTo(this.product.getId());
    }

    @Test
    @DisplayName("findByIdForAgent throws Exception when use a invalid product id")
    void findByIdForAgent_throwsNotFoundException_whenIdIsInvalid(){
        Long invalidId = 999l;

        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> productService.findByIdForAgent(invalidId))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    @DisplayName("orderProductForAgent returns batch stock list by id when use type order L")
    void orderProductForAgent_returnsSortedByBatchId_whenTypeOrderIsL(){
        String typeOrder = "L";
        Product productFresh = ProductMock.getProductFresh();
        Section section = new Section();

        BatchStock batchStock1 = BatchStockMock.BachStockTest();
        batchStock1.setId(2L);

        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock2.setId(1L);
        Set<BatchStock> batches = new HashSet<>(){{
            add(batchStock1);
            add(batchStock2);
        }};

        productFresh.setBatches(batches);

        ProductAgentResponseDTO productDTO = new ProductAgentResponseDTO(productFresh,
                section, productFresh.getBatches());

        ProductAgentResponseDTO response = this.productService.orderProductForAgent(productDTO, typeOrder);
        List<BatchStockAgentResponseDTO> batchResponse = response.getBatchStock();

        assertThat(response).isNotNull();
        assertThat(batchResponse.get(0).getBatchNumber()).isEqualTo(batchStock2.getId());
        assertThat(batchResponse.get(1).getBatchNumber()).isEqualTo(batchStock1.getId());
    }

    @Test
    @DisplayName("orderProductForAgent returns batch stock list by id when use type order Q")
    void orderProductForAgent_returnsSortedByBatchId_whenTypeOrderIsQ(){
        String typeOrder = "Q";
        Product productFresh = ProductMock.getProductFresh();
        Section section = new Section();

        BatchStock batchStock1 = BatchStockMock.BachStockTest();
        batchStock1.setId(1L);
        batchStock1.setProductQuantity(20);

        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock2.setId(2L);
        batchStock2.setProductQuantity(10);
        Set<BatchStock> batches = new HashSet<>(){{
            add(batchStock1);
            add(batchStock2);
        }};

        productFresh.setBatches(batches);

        ProductAgentResponseDTO productDTO = new ProductAgentResponseDTO(productFresh,
                section, productFresh.getBatches());

        ProductAgentResponseDTO response = this.productService.orderProductForAgent(productDTO, typeOrder);
        List<BatchStockAgentResponseDTO> batchResponse = response.getBatchStock();

        assertThat(response).isNotNull();
        assertThat(batchResponse.get(0).getCurrentQuantity()).isEqualTo(batchStock2.getProductQuantity());
        assertThat(batchResponse.get(1).getCurrentQuantity()).isEqualTo(batchStock1.getProductQuantity());
    }

    @Test
    @DisplayName("orderProductForAgent returns batch stock list by id when use type order V")
    void orderProductForAgent_returnsSortedByBatchId_whenTypeOrderIsV(){
        String typeOrder = "V";
        Product productFresh = ProductMock.getProductFresh();
        Section section = new Section();

        BatchStock batchStock1 = BatchStockMock.BachStockTest();
        batchStock1.setId(1L);
        batchStock1.setDueDate(LocalDate.parse("2018-01-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock2.setId(2L);
        batchStock2.setDueDate(LocalDate.parse("2014-07-22", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Set<BatchStock> batches = new HashSet<>(){{
            add(batchStock1);
            add(batchStock2);
        }};

        productFresh.setBatches(batches);

        ProductAgentResponseDTO productDTO = new ProductAgentResponseDTO(productFresh,
                section, productFresh.getBatches());

        ProductAgentResponseDTO response = this.productService.orderProductForAgent(productDTO, typeOrder);
        List<BatchStockAgentResponseDTO> batchResponse = response.getBatchStock();

        assertThat(response).isNotNull();
        assertThat(batchResponse.get(0).getDueDate()).isEqualTo(batchStock2.getDueDate());
        assertThat(batchResponse.get(1).getDueDate()).isEqualTo(batchStock1.getDueDate());
    }

    @Test
    @DisplayName("orderProductForAgent returns batch stock list by id when use type order L")
    void orderProductForAgent_throwsInvalidQueryParamException_whenTypeOrderIsInvalid(){
        String invalidTypeOrder = "0";
        Product productFresh = ProductMock.getProductFresh();
        Section section = new Section();
        Set<BatchStock> batches = new HashSet<>(){{
            add(new BatchStock());
            add(new BatchStock());
        }};

        productFresh.setBatches(batches);

        ProductAgentResponseDTO productDTO = new ProductAgentResponseDTO(productFresh,
                section, productFresh.getBatches());

        assertThatThrownBy(() -> productService.orderProductForAgent(productDTO, invalidTypeOrder))
                .isInstanceOf(InvalidQueryParamException.class);

    }
}
