package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.product.ProductWarehousesDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.BatchStockMock;
import com.example.mercadofrescos.mocks.InboundOrderMock;
import com.example.mercadofrescos.mocks.ProductMock;
import com.example.mercadofrescos.mocks.WarehouseMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.repository.IWarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {
    @InjectMocks
    private WarehouseService warehouseService;

    @Mock
    private IWarehouseRepo repo;

    @Mock
    private ProductService productService;

    private Warehouse warehouse;

    @BeforeEach
    void setup(){
        this.warehouse = WarehouseMock.warehouseTest();
    }

    @Test
    @DisplayName("Return Warehouse when id is valid")
    void findById_returnWarehouse_whenIdIsValid(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(this.warehouse));

        Warehouse response = this.warehouseService.findById(this.warehouse.getId());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(this.warehouse.getId());
        assertThat(response.getName()).isEqualTo(this.warehouse.getName());
        assertThat(response.getAddress()).isEqualTo(this.warehouse.getAddress());
    }

    @Test
    @DisplayName("returns an exception if it doesn't find a warehouse")
    void findById_throwsNotFoundException_whenIdIsInvalid () {
        final Long id = 123456789L;

        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> warehouseService.findById(id))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Return ProductWarehousesDTO when id is valid")
    void getProductsQuantity_returnProductWarehousesDTO_whenIdIsValid(){
        final Long id = 1L;
        Product product = ProductMock.productTest();
        BatchStock batchStock = BatchStockMock.BachStockTest();
        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        batchStock.setInboundOrder(inboundOrder);

        Set<Warehouse> warehouses = new HashSet<>();
        warehouses.add(this.warehouse);

        Set<BatchStock> batchStocks = new HashSet<>();
        batchStocks.add(batchStock);

        product.setBatches(batchStocks);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(product);

        Mockito.when(this.repo.getWarehousesByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(warehouses);

        ProductWarehousesDTO response =  this.warehouseService.getProductsQuantity(product.getId());

        assertThat(response).isNotNull();
        assertThat(response.getProductId()).isEqualTo(product.getId());
        assertThat(response.getWarehouses()).isNotNull();
        assertThat(response.getWarehouses().size()).isEqualTo(warehouses.size());
    }

    @Test
    @DisplayName("Throws an exception when warehouse set is empty")
    void getProductsQuantity_throwsNotFoundException_whenWarehouseSetIsEmpty(){
        final Long id = 123456789L;

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Mockito.when(this.repo.getWarehousesByProductId(ArgumentMatchers.anyLong()))
                .thenReturn(new HashSet<>());

        assertThatThrownBy(() -> warehouseService.getProductsQuantity(id))
                .isInstanceOf(NotFoundException.class);
    }
}
