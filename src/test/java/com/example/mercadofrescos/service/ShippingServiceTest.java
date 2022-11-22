package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.PurchaseOrderMock;
import com.example.mercadofrescos.mocks.ShippingMock;
import com.example.mercadofrescos.mocks.TrackingOrderMock;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.model.enums.StatusShipping;
import com.example.mercadofrescos.repository.IShippingRepo;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {
    @Mock
    private IShippingRepo repo;

    @InjectMocks
    private ShippingService shippingService;

    private TrackingOrder trackingOrder;
    private Shipping shipping;

    @BeforeEach
    void setup() {
        PurchaseOrder purchaseOrder = PurchaseOrderMock.purchaseOrderTest();
        trackingOrder = TrackingOrderMock.trackingOrderTest();
        shipping = ShippingMock.shippingTest();
    }

    @Test
    @DisplayName("Update shipping when success")
    void updateTrackingOrder_returnShipping_whenSuccess(){
        Shipping shippingToUpdate = new Shipping();
        shippingToUpdate.setId(shipping.getId());
        shippingToUpdate.setUpdateDate(shipping.getUpdateDate());
        shippingToUpdate.setStatusShipping(StatusShipping.SHIPPED);
        shippingToUpdate.setDescription("Your order has been shipped by transporter");

        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.ofNullable(shipping));

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(shippingToUpdate);

        Shipping response = this.shippingService.update(trackingOrder.getId(), shipping);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(shipping.getId());
        assertThat(response.getStatusShipping()).isEqualTo(shippingToUpdate.getStatusShipping());
        assertThat(response.getDescription()).isNotNull();
        assertThat(response.getUpdateDate()).isNotNull();
    }

    @Test
    @DisplayName("Returns all shipping when success")
    void findAllShipping_returnAllShipping_whenSuccess(){
        List<Shipping> shippingList = new ArrayList<>();
        shippingList.add(shipping);

        Mockito.when(repo.findAll())
                .thenReturn(shippingList);

        List<Shipping> response = this.shippingService.findAll();

        assertThat(response).isNotNull();
        assertThat(response.size()).isEqualTo(shippingList.size());
    }

    @Test
    @DisplayName("Return exception shipping when shipping not founded")
    void updateShipping_returnExcpetions_whenHaveError(){
        Long errorId = 99L;

        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(NotFoundException.class, () ->
                shippingService.update(errorId, shipping));

        Assertions.assertEquals(
                exception.getMessage(), ("Shipping Not Found")
        );
    }

}
