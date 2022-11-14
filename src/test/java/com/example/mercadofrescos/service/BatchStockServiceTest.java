package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.BatchStockMock;
import com.example.mercadofrescos.mocks.InboundOrderMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class BatchStockServiceTest {

    @InjectMocks
    private BatchStockService batchStockService;

    @Mock
    private IBatchStockRepo repo;

    @Mock
    private IProductService serviceProduct;

    @Mock
    private ISectionService serviceSection;

    private BatchStock batchStock;

    @BeforeEach
    void setup() {
        BatchStock batchStock = new BatchStock();
        batchStock.setId(BatchStockMock.BachStockTest().getId());
        batchStock.setProduct(BatchStockMock.BachStockTest().getProduct());
        batchStock.setCurrentTemperature(BatchStockMock.BachStockTest().getCurrentTemperature());
        batchStock.setManufacturingDate(BatchStockMock.BachStockTest().getManufacturingDate());
        batchStock.setManufacturingTime(BatchStockMock.BachStockTest().getManufacturingTime());
        batchStock.setDueDate(BatchStockMock.BachStockTest().getDueDate());
        batchStock.setProductQuantity(BatchStockMock.BachStockTest().getProductQuantity());
        batchStock.setVolume(BatchStockMock.BachStockTest().getVolume());
        this.batchStock = batchStock;
    }

    @Test
    @DisplayName("Find an existing batch stock by id")
    void findBatchStockByID() {
        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(this.batchStock));

        BatchStock result = batchStockService.findById(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(this.batchStock);
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getProduct()).isEqualTo(this.batchStock.getProduct());
        assertThat(result.getCurrentTemperature()).isEqualTo(this.batchStock.getCurrentTemperature());
        assertThat(result.getVolume()).isEqualTo(this.batchStock.getVolume());

    }

    @Test
    @DisplayName("saveBatchStockList save batch list")
    void saveBatchStock() {
        /*
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        List<BatchStock> result = batchStockService.saveBatchStockList(batchStocks);

        assertThat(result).isNotNull();
        assertThat(result).size().isEqualTo(1);
        */
    }

    @Test
    @DisplayName("Throws exception when trying to save invalid batch stock")
    void validBactchStockList() {
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        assertThatThrownBy(() -> batchStockService.validBatchStockList(InboundOrderMock.InboundOrderTest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Checks if all batch stocks exist")
    void verifyAllBactchStocks() {
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        assertThatThrownBy(() -> batchStockService.verifyIfAllBatchStockExists(batchStocks))
                .isInstanceOf(NotFoundException.class);
    }
}
