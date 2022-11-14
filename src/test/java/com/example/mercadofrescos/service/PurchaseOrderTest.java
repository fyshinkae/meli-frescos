package com.example.mercadofrescos.service;


import com.example.mercadofrescos.dto.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.exception.InvalidPurchaseException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.mocks.*;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IProductRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseItemService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderTest {
    @Mock
    IBatchStockRepo batchStockRepo;

    @Mock
    IProductRepo productRepo;

    @Mock
    IPurchaseOrderRepo purchaseOrderRepo;

    @Mock
    IUserService userService;

    @Mock
    IProductService productService;

    @Mock
    IPurchaseItemService purchaseItemService;

    @InjectMocks
    PurchaseOrderService purchaseOrderService;

    private final User customer = new User();
    private final Product product = new Product();
    private final PurchaseOrder purchaseOrder = new PurchaseOrder();
    private final PurchaseItem purchaseItem = new PurchaseItem();
    private final List<PurchaseItem> purchaseItemList = new ArrayList<>();

    @BeforeEach
    @DisplayName("Test Purchase Order Service")
    void setup() {
        customer.setId(UserMock.userTest().getId());

        product.setId(ProductMock.productTest().getId());
        product.setSeller(UserSellerMock.sellerTest());
        product.setBatches(BatchStockMock.BachStockTest().getProduct().getBatches());
        product.setName(ProductMock.productTest().getName());
        product.setPrice(ProductMock.productTest().getPrice());
        product.setCategory(ProductMock.productTest().getCategory());

        purchaseItem.setId(PurchaseItemMock.puchaseItemTest().getId());
        purchaseItem.setProductId(product);
        purchaseItem.setPurchaseOrderId(purchaseOrder);
        purchaseItem.setProductQuantity(PurchaseItemMock.puchaseItemTest().getProductQuantity());

        purchaseItemList.add(purchaseItem);

        purchaseOrder.setId(PurchaseOrderMock.purchaseOrderTest().getId());
        purchaseOrder.setCustomer(customer);
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(PurchaseOrderMock.purchaseOrderTest().getDate());
        purchaseOrder.setItemList(purchaseItemList);
    }

    @Test
    @DisplayName("Testing creator order with correct arguments")
    void newOrder_returnTotalPrice_whenSuccess() {
        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(product);
        Mockito.when(purchaseOrderRepo.save(purchaseOrder)).thenReturn(purchaseOrder);
        Mockito.doNothing().when(purchaseItemService).savePurchaseItemList(purchaseOrder.getItemList());

        PurchasePriceDTO serviceReturn = purchaseOrderService.getCartAmount(purchaseOrder);

        assertThat(serviceReturn).isNotNull();
        assertThat(serviceReturn.getTotalPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Testing return exceptions when have products errors")
    void newOrder_returnException_whenHaveError() {
        BatchStock batch = new BatchStock();
        Set<BatchStock> batches = new HashSet<>();

        batch.setId(1L);
        batch.setProductQuantity(1);
        batch.setDueDate(LocalDate.parse("2023-10-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        batches.add(batch);

        product.setBatches(batches);

        purchaseItem.setProductId(product);

        purchaseItemList.clear();
        purchaseItemList.add(purchaseItem);
        purchaseOrder.setItemList(purchaseItemList);

        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(product);

        Throwable exception = assertThrows(InvalidPurchaseException.class, () ->
                purchaseOrderService.getCartAmount(purchaseOrder));

        Assertions.assertEquals(
                exception.getMessage(), ("Products " + "[1]" + " is not available")
        );
    }

    @Test
    @DisplayName("Testing return exceptions when have products with expirated date")
    void newOrder_returnException_whenHaveExpiratedDate() {
        BatchStock batch = new BatchStock();
        Set<BatchStock> batches = new HashSet<>();

        batch.setId(1L);
        batch.setProductQuantity(100);
        batch.setDueDate(LocalDate.now());
        batches.add(batch);

        product.setBatches(batches);

        purchaseItem.setProductId(product);

        purchaseItemList.clear();
        purchaseItemList.add(purchaseItem);
        purchaseOrder.setItemList(purchaseItemList);

        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(product);

        Throwable exception = assertThrows(InvalidPurchaseException.class, () ->
                purchaseOrderService.getCartAmount(purchaseOrder));

        Assertions.assertEquals(
                exception.getMessage(), ("Products " + "[1]" + " close to expiration")
        );
    }

    @Test
    @DisplayName("Testing success when have correct order id")
    void getPurchaseOrderById_returnPurchaseItems_whenSuccess() {
        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrder));

        List<PurchaseItemResponseDTO> serviceReturn = purchaseOrderService.getPurchaseOrderById(purchaseOrder.getId());

        assertThat(serviceReturn).isNotNull();
        assertThat(serviceReturn).isEqualTo(PurchaseItemMock.convertoToDTO(purchaseOrder.getItemList()));
    }

    @Test
    @DisplayName("Testing excpetion when have incorrect order id ")
    void getPurchaseOrderById_returnException_whenNonExistentOrderId() {
        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NotFoundException.class, () ->
                purchaseOrderService.getPurchaseOrderById(purchaseOrder.getId()));

        Assertions.assertEquals(
                exception.getMessage(), ("Purchase order not found")
        );
    }

    @Test
    @DisplayName("Testing correct status order by order id")
    void updateStatusOrder_returnOrderUpdated_whenSuccess() {
        StatusOrder status = StatusOrder.FINALIZADO;
        purchaseOrder.setStatusOrder(status);

        Mockito.when(purchaseOrderRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(purchaseOrder));
        Mockito.when(purchaseOrderRepo.save(purchaseOrder)).thenReturn(purchaseOrder);

        PurchaseOrderRequestDTO serviceReturn = purchaseOrderService.updateOrderStatus(status, purchaseOrder.getId());

        assertThat(serviceReturn).isNotNull();
        assertThat(serviceReturn).isEqualTo(PurchaseOrderRequestDTO.convert(purchaseOrder));

    }
}
