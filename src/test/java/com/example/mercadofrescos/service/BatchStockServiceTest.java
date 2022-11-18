package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.batchStock.BatchStockOrderByDueDateDTO;
import com.example.mercadofrescos.dto.batchStock.BatchStockResponseDTO;
import com.example.mercadofrescos.exception.InvalidBatchStockException;
import com.example.mercadofrescos.exception.InvalidQueryParamException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.BatchStockMock;
import com.example.mercadofrescos.mocks.InboundOrderMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.model.enums.OrderBy;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
    private IProductService productService;

    @Mock
    private ISectionService sectionService;

    @Mock
    private IBatchStockRepo repo;

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
    @DisplayName("Throws exception when productId is invalid")
    void validBatchStockList_returnValidBatchStockList_whenInboundOrderIsValid() {
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        inboundOrder.setBatches(batchStocks);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(this.batchStock.getProduct());

        Mockito.when(this.repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(this.batchStock));

        List<BatchStock> batches = this.batchStockService.validBatchStockList(inboundOrder);

        assertThat(batches).isNotNull();
        assertThat(batches.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Throws exception when productId is invalid")
    void validBatchStockList_throwsNotFoundException_whenProductIdIsInvalid() {
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                        .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> batchStockService.validBatchStockList(InboundOrderMock.InboundOrderTest()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Throws InvalidBatchStockException when BatchStock has a invalid Temperature")
    void validBatchStockList_throwsInvalidBatchStockException_whenBatchStockHasInvalidTemperature() {
        List<BatchStock> batchStocks = new ArrayList<>();
        this.batchStock.setCurrentTemperature(10F);
        batchStocks.add(this.batchStock);

        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        inboundOrder.setBatches(batchStocks);
        inboundOrder.getSection().setMinTemperature(15F);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(this.batchStock.getProduct());

        assertThatThrownBy(() -> batchStockService.validBatchStockList(inboundOrder))
                .isInstanceOf(InvalidBatchStockException.class);
    }

    @Test
    @DisplayName("Throws InvalidBatchStockException when BatchStock has a invalid dueDate")
    void validBatchStockList_throwsInvalidBatchStockException_whenBatchStockHasInvalidDueDate() {
        List<BatchStock> batchStocks = new ArrayList<>();
        this.batchStock.setDueDate(LocalDate.now().minusDays(1));
        batchStocks.add(this.batchStock);

        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        inboundOrder.setBatches(batchStocks);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(this.batchStock.getProduct());

        assertThatThrownBy(() -> batchStockService.validBatchStockList(inboundOrder))
                .isInstanceOf(InvalidBatchStockException.class);
    }

    @Test
    @DisplayName("Throws InvalidBatchStockException when BatchStock has a invalid Volume")
    void validBatchStockList_throwsInvalidBatchStockException_whenBatchStockHasInvalidVolume() {
        List<BatchStock> batchStocks = new ArrayList<>();
        this.batchStock.setVolume(10000F);
        this.batchStock.setId(null);

        batchStocks.add(this.batchStock);

        InboundOrder inboundOrder = InboundOrderMock.InboundOrderTest();
        inboundOrder.setBatches(batchStocks);
        inboundOrder.getSection().setCapacity(10F);

        Mockito.when(this.productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(this.batchStock.getProduct());

        assertThatThrownBy(() -> batchStockService.validBatchStockList(inboundOrder))
                .isInstanceOf(InvalidBatchStockException.class);
    }

    @Test
    @DisplayName("Checks if all batch stocks exist")
    void verifyAllBatchStocks() {
        List<BatchStock> batchStocks = new ArrayList<>();
        batchStocks.add(this.batchStock);

        assertThatThrownBy(() -> batchStockService.verifyIfAllBatchStockExists(batchStocks))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("getBatchStockOrderByDueDate return BatchStockResponseDTO when SectionId is valid ")
    void getBatchStockOrderByDueDate_returnBatchStockResponseDTO_whenSectionIdIsValid(){
        List<BatchStock> batchStocks = new ArrayList<>();
        Integer days = 5;
        Long sectionId = 1l;

        BatchStock batchStock1 = this.batchStock;
        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock1.setDueDate(LocalDate.now().plusDays(days));
        batchStock2.setDueDate(LocalDate.now().plusDays(days-1));

        batchStocks.add(batchStock1);
        batchStocks.add(batchStock2);

        Mockito.when(this.repo.getBatchStocksBySectionId(ArgumentMatchers.anyLong()))
                .thenReturn(batchStocks);

        BatchStockResponseDTO response = this.batchStockService.getBatchStockOrderByDueDate(days, sectionId);
        BatchStockOrderByDueDateDTO firstBatch = response.getBatchStock().get(0);

        assertThat(response).isNotNull();
        assertThat(response.getBatchStock()).isNotNull();
        assertThat(response.getBatchStock().size()).isEqualTo(batchStocks.size());
        assertThat(firstBatch.getDueDate()).isEqualTo(batchStock1.getDueDate());
    }

    @Test
    @DisplayName("Throws NotFoundException when BatchStockList is empty")
    void getBatchStockOrderByDueDate_throwsNotFoundException_whenBatchStockListIsEmpty(){
        List<BatchStock> batchStocks = new ArrayList<>();
        Integer days = 5;
        Long sectionId = 999l;

        Mockito.when(this.repo.getBatchStocksBySectionId(ArgumentMatchers.anyLong())).thenReturn(batchStocks);

        assertThatThrownBy(() -> batchStockService.getBatchStockOrderByDueDate(days,sectionId))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Throws NotFoundException when BatchStockList is null")
    void getBatchStockOrderByDueDate_throwsNotFoundException_whenBatchStockListIsNull(){
        Integer days = 5;
        Long sectionId = 999l;

        Mockito.when(this.repo.getBatchStocksBySectionId(ArgumentMatchers.anyLong())).thenReturn(null);

        assertThatThrownBy(() -> batchStockService.getBatchStockOrderByDueDate(days,sectionId))
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    @DisplayName("Throws InvalidQueryParamException when category is invalid")
    void getBatchStockOrderByDueDateAndCategory_throwsInvalidQueryParamException_whenCategoryIsInvalid(){
        String invalidCategory = "0";
        Integer days = 5;
        OrderBy orderBy = null;

        Mockito.when(this.productService.filterCategory(ArgumentMatchers.anyString()))
                .thenThrow(InvalidQueryParamException.class);

        assertThatThrownBy(() ->batchStockService
                .getBatchStockOrderByDueDateAndCategory(days,invalidCategory, orderBy))
                .isInstanceOf(InvalidQueryParamException.class);
    }

    @Test
    @DisplayName("getBatchStockOrderByDueDateAndCategory returns BatchStockList sorted by ASC when all arguments is valid")
    void getBatchStockOrderByDueDateAndCategory_returnBatchStockListASC_whenArgumentsIsValid(){
        List<BatchStock> batchStocks = new ArrayList<>();
        Integer days = 5;
        String category = "FR";
        OrderBy orderBy = OrderBy.ASC;

        BatchStock batchStock1 = this.batchStock;
        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock1.setDueDate(LocalDate.now().plusDays(days));
        batchStock2.setDueDate(LocalDate.now().plusDays(days-1));

        batchStocks.add(batchStock1);
        batchStocks.add(batchStock2);

        Mockito.when(this.productService.filterCategory(ArgumentMatchers.anyString()))
                .thenReturn(Category.FRESH);

        Mockito.when(this.repo.getBatchStocksByCategory(ArgumentMatchers.any(Category.class)))
                .thenReturn(batchStocks);

        BatchStockResponseDTO response =  this.batchStockService.getBatchStockOrderByDueDateAndCategory(days,category, orderBy);
        BatchStockOrderByDueDateDTO firstBatch = response.getBatchStock().get(0);

        assertThat(response).isNotNull();
        assertThat(response.getBatchStock()).isNotNull();
        assertThat(response.getBatchStock().size()).isEqualTo(batchStocks.size());
        assertThat(firstBatch.getDueDate()).isEqualTo(batchStock1.getDueDate());
    }

    @Test
    @DisplayName("getBatchStockOrderByDueDateAndCategory returns BatchStockList sorted by DESC when all arguments is valid")
    void getBatchStockOrderByDueDateAndCategory_returnBatchStockListDESC_whenArgumentsIsValid(){
        List<BatchStock> batchStocks = new ArrayList<>();
        Integer days = 5;
        String category = "FR";
        OrderBy orderBy = OrderBy.DESC;

        BatchStock batchStock1 = this.batchStock;
        BatchStock batchStock2 = BatchStockMock.BachStockTest();
        batchStock1.setDueDate(LocalDate.now().plusDays(days));
        batchStock2.setDueDate(LocalDate.now().plusDays(days-1));

        batchStocks.add(batchStock1);
        batchStocks.add(batchStock2);

        Mockito.when(this.productService.filterCategory(ArgumentMatchers.anyString()))
                .thenReturn(Category.FRESH);

        Mockito.when(this.repo.getBatchStocksByCategory(ArgumentMatchers.any(Category.class)))
                .thenReturn(batchStocks);

        BatchStockResponseDTO response =  this.batchStockService.getBatchStockOrderByDueDateAndCategory(days,category, orderBy);
        BatchStockOrderByDueDateDTO firstBatch = response.getBatchStock().get(0);

        assertThat(response).isNotNull();
        assertThat(response.getBatchStock()).isNotNull();
        assertThat(response.getBatchStock().size()).isEqualTo(batchStocks.size());
        assertThat(firstBatch.getDueDate()).isEqualTo(batchStock2.getDueDate());
    }

    @Test
    @DisplayName("Throws NotFoundException when BatchStock list is empty")
    void getBatchStockOrderByDueDateAndCategory_throwsNotFoundException_whenBatchStockListIsEmpty(){
        String category = "FR";
        List<BatchStock> batchStocks = new ArrayList<>();
        Integer days = 5;
        OrderBy orderBy = null;

        Mockito.when(this.productService.filterCategory(ArgumentMatchers.anyString()))
                .thenReturn(Category.FRESH);

        Mockito.when(this.repo.getBatchStocksByCategory(ArgumentMatchers.any(Category.class)))
                        .thenReturn(batchStocks);

        assertThatThrownBy(() ->batchStockService
                .getBatchStockOrderByDueDateAndCategory(days,category, orderBy))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Throws NotFoundException when BatchStock list is null")
    void getBatchStockOrderByDueDateAndCategory_throwsNotFoundException_whenBatchStockListIsNull(){
        String category = "FR";
        Integer days = 5;
        OrderBy orderBy = null;

        Mockito.when(this.productService.filterCategory(ArgumentMatchers.anyString()))
                .thenReturn(Category.FRESH);

        Mockito.when(this.repo.getBatchStocksByCategory(ArgumentMatchers.any(Category.class)))
                .thenReturn(null);

        assertThatThrownBy(() ->batchStockService
                .getBatchStockOrderByDueDateAndCategory(days,category, orderBy))
                .isInstanceOf(NotFoundException.class);
    }
}
