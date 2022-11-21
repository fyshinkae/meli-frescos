package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.*;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.*;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.IUserService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PurchaseReservationServiceTest {
    @Mock
    IPurchaseOrderRepo purchaseOrderRepo;

    @Mock
    IPurchaseOrderService purchaseOrderService;

    @Mock
    IUserService userService;

    @Mock
    IProductService productService;

    @Mock
    IBatchStockRepo batchStockRepo;

    @InjectMocks
    PurchaseReservationService purchaseReservationService;

    private final User customer = new User();
    private final Product product = new Product();
    private final PurchaseOrder purchaseOrder = new PurchaseOrder();
    private final PurchaseItem purchaseItem = new PurchaseItem();
    private final List<PurchaseItem> purchaseItemList = new ArrayList<>();
    List<PurchaseItemResponseDTO> purchaseItemsResponse;

    @BeforeEach
    @DisplayName("Test Purchase Reservation Service")
    void setup() {
        customer.setId(UserMock.userTest().getId());

        product.setId(ProductMock.productTest().getId());
        product.setSeller(UserSellerMock.sellerTest());
        product.setBatches(BatchStockMock.BachStockTest().getProduct().getBatches());
        product.setName(ProductMock.productTest().getName());
        product.setPrice(ProductMock.productTest().getPrice());
        product.setCategory(ProductMock.productTest().getCategory());

        purchaseItem.setId(PurchaseItemMock.puchaseItemTest().getId());
        purchaseItem.setProduct(product);
        purchaseItem.setPurchaseOrder(purchaseOrder);
        purchaseItem.setProductQuantity(PurchaseItemMock.puchaseItemTest().getProductQuantity());

        purchaseItemList.add(purchaseItem);

        purchaseItemsResponse =  purchaseItemList.stream().map(PurchaseItemResponseDTO::new).collect(Collectors.toList());

        purchaseOrder.setId(PurchaseOrderMock.purchaseOrderTest().getId());
        purchaseOrder.setCustomer(customer);
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(LocalDate.now());
        purchaseOrder.setItemList(purchaseItemList);
        purchaseOrder.setReservation(true);
    }

    @Test
    void createReservation_returnPurchaseCreated_whenSuccess() {
        Mockito.when(userService.findById(ArgumentMatchers.anyLong())).thenReturn(customer);
        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(product);
        Mockito.when(purchaseOrderRepo.save(purchaseOrder)).thenReturn(purchaseOrder);
        PurchaseReservationResponseDTO response = purchaseReservationService.createReservation(purchaseOrder);


        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1);
        assertThat(response.getCreatedAt()).isEqualTo(purchaseOrder.getDate());
        assertThat(response.getItems()).isEqualTo(purchaseItemsResponse);
        assertThat(response.getTotalPrice()).isEqualTo(BigDecimal.valueOf(1000.0));
    }

    @Test
    void findAll_returnAllReservation_whenSuccess() {
        List<PurchaseRequestDTO> purchaseItems = List.of(PurchaseRequestDTO.convert(purchaseOrder));
        List<PurchaseOrder> purchaseOrders = List.of(purchaseOrder);

        Mockito.when(purchaseOrderRepo.findAllReservation()).thenReturn(purchaseOrders);

        List<PurchaseRequestDTO> response = purchaseReservationService.findAll();

        assertThat(response).isNotNull();
        assertThat(response).isEqualTo(purchaseItems);
    }

    @Test
    void findById_returnReservation_whenSuccess() {
        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrder));

        PurchaseReservationResponseDTO response = purchaseReservationService.findById(purchaseOrder.getId());

        assertThat(response).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(BigDecimal.valueOf(1000.0));
        assertThat(response.getId()).isEqualTo(purchaseOrder.getId());
        assertThat(response.getCreatedAt()).isEqualTo(purchaseOrder.getDate());
        assertThat(response.getItems()).isEqualTo(purchaseItemsResponse);
    }

    @Test
    void findById_returnException_whenNonExistentPurchaseOrder() {
        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrder));
        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NotFoundException.class, () ->
                purchaseReservationService.findById(purchaseOrder.getId()));

        Assertions.assertEquals(
                exception.getMessage(), ("Purchase order not found")
        );
    }

    @Test
    void verifyAvailability_returnAvailability_whenSuccess() {
        Mockito.when(purchaseOrderService.findById(ArgumentMatchers.anyLong())).thenReturn(purchaseOrder);

        List<AvailabilityDTO> itemsResponse = purchaseOrder.getItemList().stream().map(AvailabilityDTO::new).collect(Collectors.toList());

        CheckAvailabilityResponseDTO response = purchaseReservationService.verifyAvailability(purchaseOrder.getId());

        assertThat(response).isNotNull();
        assertThat(response.getTotalPrice()).isEqualTo(BigDecimal.valueOf(1000.0));
        assertThat(response.getId()).isEqualTo(purchaseOrder.getId());
        assertThat(response.getItems()).isEqualTo(itemsResponse);
        assertThat(response.getBuyerId()).isEqualTo(purchaseOrder.getCustomer().getId());
    }

    @Test
    void finishReservation_returnReservationFinish_whenSuccess() {
        Mockito.when(purchaseOrderService.findById(ArgumentMatchers.anyLong())).thenReturn(purchaseOrder);
        Mockito.when(batchStockRepo.save(ArgumentMatchers.any())).thenReturn(new BatchStock());

        purchaseOrder.setReservation(false);
        purchaseOrder.setStatusOrder(StatusOrder.FINALIZADO);
        Mockito.when(purchaseOrderRepo.save(ArgumentMatchers.any())).thenReturn(purchaseOrder);

        PurchaseOrderRequestDTO response = purchaseReservationService.finishReservation(purchaseOrder.getId());
        List<PurchaseItemDTO>  purchaseItemDTOS = purchaseOrder.getItemList()
                .stream()
                .map(PurchaseItemDTO::convert)
                .collect(Collectors.toList());

        assertThat(response).isNotNull();
        assertThat(response.getPurchaseOrder().getDate()).isEqualTo(purchaseOrder.getDate());
        assertThat(response.getPurchaseOrder().getId()).isEqualTo(purchaseOrder.getId());
        assertThat(response.getPurchaseOrder().getBuyerId()).isEqualTo(purchaseOrder.getCustomer().getId());
        assertThat(response.getPurchaseOrder().getOrderStatus()).isEqualTo(StatusOrder.FINALIZADO);
        assertThat(response.getPurchaseOrder().getProducts()).isEqualTo(purchaseItemDTOS);
        assertThat(response.getPurchaseOrder().isReservation()).isEqualTo(false);
    }
}
