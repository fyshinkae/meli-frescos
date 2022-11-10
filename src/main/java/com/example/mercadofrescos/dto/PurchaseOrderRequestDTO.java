package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequestDTO {

    private PurchaseRequestDTO purchaseOrder;

    /**
     * Converte um PurchaseRequestDTO para um objeto PurchaseOrder
     * @author Gabriel
     * @param purchaseOrderRequestDTO DTO a ser convertido
     * @return um objeto do tipo PurchaseOrder gerado a partir de um DTO
     */
    public static PurchaseOrder convert(PurchaseOrderRequestDTO purchaseOrderRequestDTO){
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        PurchaseRequestDTO purchaseDTO = purchaseOrderRequestDTO.getPurchaseOrder();

        purchaseOrder.setDate(purchaseDTO.getDate());
        purchaseOrder.setId(purchaseDTO.getId());
        purchaseOrder.setStatusOrder(purchaseDTO.getOrderStatus());

        List<PurchaseItem> items = new ArrayList<>();
        for(PurchaseItemDTO purchaseItem : purchaseDTO.getProducts()){
            PurchaseItem convertedPurchaseItem = PurchaseItemDTO.convert(purchaseItem);
            convertedPurchaseItem.setPurchaseOrderId(purchaseOrder);
            items.add(convertedPurchaseItem);
        }

        purchaseOrder.setItemList(items);

        return purchaseOrder;
    }
}
