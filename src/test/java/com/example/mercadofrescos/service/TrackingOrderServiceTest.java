package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.mocks.PurchaseOrderMock;
import com.example.mercadofrescos.mocks.TrackingOrderMock;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.Section;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.repository.ITrakingOrderRepo;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TrackingOrderServiceTest {

    @Mock
    private ITrakingOrderRepo repo;

    @Mock
    private IPurchaseOrderService orderService;

    @InjectMocks
    private TrackingOrderService trackingOrderService;

    private PurchaseOrder purchaseOrder;
    private TrackingOrder trackingOrder;

    @BeforeEach
    void setup() {
        purchaseOrder = PurchaseOrderMock.purchaseOrderTest();
        trackingOrder = TrackingOrderMock.trackingOrderTest();
    }

    @Test
    @DisplayName("create tracking order from purchase created")
    void createTrackingOrder_returnTrackingOrder_whenIdIsValid() {
        Mockito.when(repo.findTrackingOrderByPurchaseOrderId(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        Mockito.when(orderService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(purchaseOrder);

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(trackingOrder);

        TrackingOrderRequestDTO trackingOrderRequestDTO = new TrackingOrderRequestDTO();
        trackingOrderRequestDTO.setPurchaseOrderId(1L);

        TrackingOrderResponseDTO response = this.trackingOrderService.create(trackingOrderRequestDTO);

        assertThat(response).isNotNull();
        assertThat(response.getPurchaseOrderId()).isEqualTo(trackingOrderRequestDTO.getPurchaseOrderId());
        assertThat(response.getTrackingDate()).isNotNull();
        assertThat(response.getShippingId()).isEqualTo(trackingOrder.getShipping().getId());
        assertThat(response.getAddress().getId()).isEqualTo(trackingOrder.getAddress().getId());
    }

    @Test
    @DisplayName("find all tracking orders from purchase order created")
    void findAll_returnAllTrackingOrder_whenIdIsValid() {
        List<TrackingOrder> trackingOrderList = new ArrayList<>();
        trackingOrderList.add(trackingOrder);

        Mockito.when(repo.findAll())
                .thenReturn(trackingOrderList);

        List<TrackingOrder> response = this.trackingOrderService.findAllTrackingOrder();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(trackingOrderList.size());
    }

    @Test
    @DisplayName("find by trackingOrder id from purchase order created")
    void findById_returnTrackingOrderById_whenIdIsValid() {

        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(trackingOrder));

        TrackingOrder response = this.trackingOrderService.findTrackingOrderById(trackingOrder.getId());

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(trackingOrder.getId());
        assertThat(response.getPurchaseOrder().getId()).isEqualTo(trackingOrder.getPurchaseOrder().getId());
        assertThat(response.getTrackingDate()).isNotNull();
        assertThat(response.getShipping().getId()).isEqualTo(trackingOrder.getShipping().getId());
        assertThat(response.getAddress().getId()).isEqualTo(trackingOrder.getAddress().getId());
    }
}
