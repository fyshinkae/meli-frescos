package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemResponseDTO {

    private Long productId;

    private Integer quantity;

    public PurchaseItemResponseDTO(PurchaseItem item) {
        productId = item.getProduct().getId();
        quantity = item.getProductQuantity();
    }

}
