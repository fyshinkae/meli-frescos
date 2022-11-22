package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.mocks.PurchaseOrderMock;
import com.example.mercadofrescos.mocks.RecurrenceOrderMock;
import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.RecurrenceOrder;
import com.example.mercadofrescos.repository.IRecurrenceOrderRepo;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class RecurrenceServiceTest {

    @InjectMocks
    private RecurrenceService recurrenceService;

    @Mock
    private IRecurrenceOrderRepo repo;

    @Mock
    private IPurchaseOrderService purchaseOrderService;

    private RecurrenceOrder recurrenceOrder;

    private PurchaseOrder purchaseOrder;

    @BeforeEach
    void setup() {
        RecurrenceOrder recurrenceOrder = new RecurrenceOrder();
        recurrenceOrder.setId(1L);
        recurrenceOrder.setDayOfMonth(RecurrenceOrderMock.recurrenceOrderTest().getDayOfMonth());
        this.recurrenceOrder = recurrenceOrder;

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);
        purchaseOrder.setCustomer(PurchaseOrderMock.purchaseOrderTest().getCustomer());
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(PurchaseOrderMock.purchaseOrderTest().getDate());
        purchaseOrder.setItemList(PurchaseOrderMock.purchaseOrderTest().getItemList());
        this.purchaseOrder = purchaseOrder;
    }

    @Test
    @DisplayName("aaaaa")
    void createRecurrence_returnSucess() {
        Mockito.when(purchaseOrderService.findById(ArgumentMatchers.any()))
                .thenReturn(purchaseOrder);

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(recurrenceOrder);

        RecurrenceResponseDTO result = this.recurrenceService.createRecurrence(recurrenceOrder);

        assertThat(result).isNotNull();
    }


    @Test
    @DisplayName("bbbb")
    void getAllRecurrence_returnRecurrenceList() {
        //
    }

    @Test
    @DisplayName("ccccc")
    void updateRecurrente_returnRecurrenceUpdated() {

    }

    @Test
    @DisplayName("dddddd")
    void deleteRecurrenceByID_doesNotReturnAnything() {
        Mockito.doNothing().when(repo).deleteById(ArgumentMatchers.any());
        recurrenceService.deleteByID(1l);
    }
}
