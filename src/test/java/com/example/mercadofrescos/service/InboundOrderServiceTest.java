package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.inboundOrder.InboundOrderResponseDTO;
import com.example.mercadofrescos.mocks.InboundOrderMock;
import com.example.mercadofrescos.mocks.SectionMock;
import com.example.mercadofrescos.mocks.WarehouseMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.repository.IInboundOrderRepo;
import com.example.mercadofrescos.service.interfaces.IBatchStockService;
import com.example.mercadofrescos.service.interfaces.ISectionService;
import com.example.mercadofrescos.service.interfaces.IWarehouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceTest {

    @InjectMocks
    private InboundOrderService inboundOrderService;

    @Mock
    private IInboundOrderRepo repoOrder;

    @Mock
    private ISectionService serviceSection;

    @Mock
    private IBatchStockService serviceBatchStock;

    @Mock
    private IWarehouseService serviceWarehouse;

    @Test
    @DisplayName("returns InboundOrderResponse on success")
    void saveInboundOrder () {
        List<BatchStock> batches = new ArrayList<>();

        Mockito.when(serviceWarehouse.findById(ArgumentMatchers.any()))
                .thenReturn(WarehouseMock.warehouseTest());

        Mockito.when(serviceSection.findById(ArgumentMatchers.any()))
                .thenReturn(SectionMock.SectionTest());

        Mockito.when(repoOrder.save(ArgumentMatchers.any()))
                .thenReturn(InboundOrderMock.InboundOrderTest());

        Mockito.when(serviceBatchStock.validBatchStockList(ArgumentMatchers.any()))
                .thenReturn(batches);

        InboundOrderResponseDTO result = inboundOrderService.save(InboundOrderMock.InboundOrderTest(), 1L);

        assertThat(result).isNotNull();
        assertThat(result.getBatchStock()).size().isEqualTo(1);
    }

    @Test
    @DisplayName("should update an InboundOrder")
    void updateInboundOrder () {
        // pedir ajuda aqui
    }
}
