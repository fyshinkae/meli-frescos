package com.example.mercadofrescos.integrados;

import com.example.mercadofrescos.dto.ProductAgentResponseDTO;
import com.example.mercadofrescos.dto.ProductDTO;
import com.example.mercadofrescos.dto.ProductResponseDTO;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.repository.IBatchStockRepo;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductControllerTestIT  {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private IBatchStockRepo batchRepo;

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
        BatchStock batchesResponse = productResponse.get().getBatches().iterator().next();

        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/agent/list?productId=1&order=Q")
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.batchStock.size()", CoreMatchers.is(productResponse.get().getBatches().size())))
//                .andExpect(jsonPath("$.batchStock[0].batchNumber", CoreMatchers.is(1)))
//                .andExpect(jsonPath("$.batchStock[0].currentQuantity", CoreMatchers.is(batchesResponse.getProductQuantity())))
//                .andExpect(jsonPath("$.batchStock[0].dueDate", CoreMatchers.is(batchesResponse.getDueDate())))
        ;
    }

    @Test
    void getAllByCategory_returnException_whenHaveError() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/list?category=FROZEN")
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("No products with this category were found")));
    }

    @Test
    void getAllByCategory_returnException_whenNoHaveProductId() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/agent/list?productId=1&order=XABLAU")
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Invalid sort parameter")));
        ;
    }

    @Test
    void getAllByCategory_returnException_whenNoHaveProduct() throws Exception {
        ResultActions response = mockMvc.perform(
                get("/api/v1/fresh-products/agent/list?productId=20")
                        .contentType(MediaType.APPLICATION_JSON) );

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", CoreMatchers.is("Product not found")));
        ;
    }

}
