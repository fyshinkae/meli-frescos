package com.example.mercadofrescos.dto;

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
        productId = item.getProductId().getId();
        quantity = item.getProductQuantity();
    }

}
