package com.example.mercadofrescos.service;


import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.mocks.*;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.model.enums.Category;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IProductRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.repository.IUserRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseItemService;
import com.example.mercadofrescos.service.interfaces.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;


import java.math.BigDecimal;
import java.util.*;

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

    private User customer = new User();

    private Product product1 = new Product();
    private Product product2 = new Product();

    private PurchaseOrder purchaseOrder = new PurchaseOrder();

    private PurchaseItem purchaseItem1 = new PurchaseItem();
    private PurchaseItem purchaseItem2 = new PurchaseItem();

    private List<PurchaseItem> purchaseItemList = new ArrayList<>();

    @BeforeEach
    void setup() {
        customer.setId(UserMock.userTest().getId());

        product1.setId(1L);
        product1.setSeller(UserSellerMock.sellerTest());
        product1.setBatches(BatchStockMock.BachStockTest().getProduct().getBatches());
        product1.setName("product 1");
        product1.setPrice(new BigDecimal(100));
        product1.setCategory(Category.FRESH);

        // com um produto só
        product2.setId(2L);
        product2.setSeller(UserSellerMock.sellerTest());
        product2.setBatches(BatchStockMock.BachStockTest().getProduct().getBatches());
        product2.setName("product 2");
        product2.setPrice(new BigDecimal(200));
        product2.setCategory(Category.FRESH);

        purchaseItem1.setId(1L);
        purchaseItem1.setProductId(product1);
        purchaseItem1.setPurchaseOrderId(purchaseOrder);
        purchaseItem1.setProductQuantity(10);


        purchaseItem2.setId(2L);
        purchaseItem2.setPurchaseOrderId(purchaseOrder);
        purchaseItem2.setProductQuantity(10);
        purchaseItem2.setProductId(product2);

        purchaseItemList.add(purchaseItem1);

        purchaseOrder.setId(PurchaseOrderMock.purchaseOrderTest().getId());
        purchaseOrder.setCustomer(customer);
        purchaseOrder.setStatusOrder(PurchaseOrderMock.purchaseOrderTest().getStatusOrder());
        purchaseOrder.setDate(PurchaseOrderMock.purchaseOrderTest().getDate());
        purchaseOrder.setItemList(purchaseItemList);
    }

    @Test
    void newOrder_returnTotalPrice_whenSuccess() {
        // Mockito.when(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(customer));
        Mockito.when(productService.findById(ArgumentMatchers.anyLong())).thenReturn(product1);
        Mockito.when(purchaseOrderRepo.save(purchaseOrder)).thenReturn(purchaseOrder);
        Mockito.doNothing().when(purchaseItemService).savePurchaseItemList(purchaseOrder.getItemList());

        PurchasePriceDTO serviceReturn = purchaseOrderService.getCartAmount(purchaseOrder);

        assertThat(serviceReturn).isNotNull();
        assertThat(serviceReturn.getTotalPrice()).isEqualTo(1000);
    }
}
