package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.mocks.*;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.IPurchaseReservationService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
    }
}
