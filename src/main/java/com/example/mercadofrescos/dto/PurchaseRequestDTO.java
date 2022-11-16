package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDTO {

    private LocalDate date;

    private Long id, buyerId;

    private StatusOrder orderStatus;

    private List<PurchaseItemDTO> products;

    // todo: javadoc
    public static PurchaseOrder convert(PurchaseRequestDTO purchaseDTO){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setDate(purchaseDTO.getDate());
        purchaseOrder.setId(purchaseDTO.getId());
        purchaseOrder.setStatusOrder(purchaseDTO.getOrderStatus());

        User customer = new User();
        customer.setId(purchaseDTO.getBuyerId());
        purchaseOrder.setCustomer(customer);

        List<PurchaseItem> items = new ArrayList<>();
        for(PurchaseItemDTO purchaseItem : purchaseDTO.getProducts()){
            PurchaseItem convertedPurchaseItem = PurchaseItemDTO.convert(purchaseItem);
            convertedPurchaseItem.setPurchaseOrderId(purchaseOrder);
            items.add(convertedPurchaseItem);
        }

        purchaseOrder.setItemList(items);

        return purchaseOrder;
    }

    // todo: javadoc
    public static PurchaseRequestDTO convert(PurchaseOrder purchaseOrder){
        PurchaseRequestDTO response = new PurchaseRequestDTO();

        response.setId(purchaseOrder.getId());
        response.setBuyerId(purchaseOrder.getCustomer().getId());
        response.setDate(purchaseOrder.getDate());
        response.setOrderStatus(purchaseOrder.getStatusOrder());

        List<PurchaseItemDTO> items = new ArrayList<>();
        for(PurchaseItem purchaseItem : purchaseOrder.getItemList()){
            PurchaseItemDTO products = PurchaseItemDTO.convert(purchaseItem);
            items.add(products);
        }

        response.setProducts(items);

        return response;
    }
}
