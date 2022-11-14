package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.dto.PurchaseItemResponseDTO;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;

import java.util.List;
import java.util.stream.Collectors;

public class PurchaseItemMock {
    public static PurchaseItem puchaseItemTest() {
        PurchaseItem purchaseItem = new PurchaseItem();
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseItem.setId(1L);
        purchaseItem.setProductId(ProductMock.productTest());
        purchaseItem.setPurchaseOrderId(purchaseOrder);
        purchaseItem.setProductQuantity(10);

        return purchaseItem;
    }

    public static List<PurchaseItemResponseDTO> convertoToDTO(List<PurchaseItem> items) {
        return items.stream()
                .map(PurchaseItemResponseDTO::new)
                .collect(Collectors.toList());
    }
}
