package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDTO extends PurchaseItemResponseDTO {
    private int availableQuantity;

    public AvailabilityDTO(PurchaseItem purchaseItem) {
        Product product = purchaseItem.getProduct();
        this.setProductId(product.getId());
        this.setQuantity(purchaseItem.getProductQuantity());

        product.getBatches().forEach(item -> availableQuantity += item.getProductQuantity());
    }
}