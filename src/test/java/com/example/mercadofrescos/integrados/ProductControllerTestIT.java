package com.example.mercadofrescos.integrados;

import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.repository.IProductRepo;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTestIT  {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductRepo productRepo;

    @BeforeEach
    void setup() {
        Product product1 = new Product();
        product1.setId(ProductMock.productTest().getId());
        product1.setSeller(ProductMock.productTest().getSeller());
        product1.setBatches(ProductMock.productTest().getBatches());
        product1.setName(ProductMock.productTest().getName());
        product1.setPrice(ProductMock.productTest().getPrice());
        product1.setCategory(ProductMock.productTest().getCategory());

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
    }

    @Test
    void getAll_retunsAllProducts_whenSuccess() throws Exception {
        List<Product> productResponse = productRepo.findAll();

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products", productResponse)
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()", CoreMatchers.is(productResponse.size())))
                .andExpect(jsonPath("$[0].category", CoreMatchers.is(productResponse.get(0).getCategory().toString())))
                .andExpect(jsonPath("$[0].price", CoreMatchers.is(new Double(String.valueOf(productResponse.get(0).getPrice())))))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(productResponse.get(0).getName())));
    }

    @Test
    void getAllByCategory_returnProducts_whenSuccess() throws Exception {
        List<Product> productResponse = productRepo.findAllByCategory(Category.FROZEN);

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/list?category=FR", productResponse)
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()", CoreMatchers.is(productResponse.size())))
                .andExpect(jsonPath("$[0].category", CoreMatchers.is(productResponse.get(0).getCategory().toString())))
                .andExpect(jsonPath("$[0].price", CoreMatchers.is(new Double(String.valueOf(productResponse.get(0).getPrice())))))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is(productResponse.get(0).getName())));
    }

    @Test
    void getAllProducts_returnsAllProducts_whenSuccess() throws Exception {
        Optional<Product> productResponse = productRepo.findById(1L);
        System.out.println("Ola To aqui" + productResponse);


        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/list?productId=1&order=V", productResponse)
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

}
