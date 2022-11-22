package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.PurchaseOrderMock;
import com.example.mercadofrescos.mocks.RecurrenceOrderMock;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(1L);
        purchaseOrder.setCustomer(PurchaseOrderMock.purchaseOrderTest().getCustomer());
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(PurchaseOrderMock.purchaseOrderTest().getDate());
        purchaseOrder.setItemList(PurchaseOrderMock.purchaseOrderTest().getItemList());
        this.purchaseOrder = purchaseOrder;

        RecurrenceOrder recurrenceOrder = new RecurrenceOrder();
        recurrenceOrder.setId(1L);
        recurrenceOrder.setDayOfMonth(RecurrenceOrderMock.recurrenceOrderTest().getDayOfMonth());
        recurrenceOrder.setPurchaseOrder(purchaseOrder);
        this.recurrenceOrder = recurrenceOrder;
    }

    @Test
    @DisplayName("Create a recurrence successfully")
    void createRecurrence_returnSucess() {
        Mockito.when(purchaseOrderService.findById(ArgumentMatchers.any()))
                .thenReturn(purchaseOrder);

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(recurrenceOrder);

        RecurrenceResponseDTO result = this.recurrenceService.createRecurrence(recurrenceOrder);

        assertThat(result).isNotNull();
        assertThat(result.getDayOfMonth()).isEqualTo(recurrenceOrder.getDayOfMonth());
    }


    @Test
    @DisplayName("Returns an exception when there are no recurrences")
    void getRecurrenceNonexistent_returnException() {
        Mockito.when(this.repo.findAll())
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> recurrenceService.getAllRecurrences())
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Return a list of recurrences")
    void getAllRecurrence_returnRecurrenceList() {
        List<RecurrenceOrder> recurrenceOrderDTOList = new ArrayList<>();
        recurrenceOrderDTOList.add(recurrenceOrder);

        Mockito.when(this.repo.findAll()).thenReturn(recurrenceOrderDTOList);

        List<RecurrenceOrderDTO> result = recurrenceService.getAllRecurrences();

        assertThat(result).isNotNull();
        assertThat(result.get(0).getOrderId()).isEqualTo(recurrenceOrderDTOList.get(0).getId());
    }

    @Test
    @DisplayName("Throws an exception when trying to update a recurrence that doesn't exist")
    void updateRecurrenceNotExist_returnException() {

        Mockito.when(this.repo.findById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThatThrownBy(() -> recurrenceService.updateRecurrence(recurrenceOrder, 1234L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("Delete a recurrence by ID")
    void deleteRecurrenceByID_doesNotReturnAnything() {
        Mockito.doNothing().when(repo).deleteById(ArgumentMatchers.any());
        recurrenceService.deleteByID(1l);
    }
}
