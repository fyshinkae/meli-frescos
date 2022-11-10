package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemDTO {

    private Long productId;

    private Integer quantity;

    /**
     * Converte um PurchaseRequestDTO para um objeto PurchaseOrder
     * @author Gabriel
     * @param purchaseItemDTO DTO a ser convertido
     * @return um objeto do tipo purchaseItem gerado a partir de um DTO
     */
    public static PurchaseItem convert(PurchaseItemDTO purchaseItemDTO){
        PurchaseItem purchaseItem = new PurchaseItem();

        purchaseItem.setProductQuantity(purchaseItemDTO.getQuantity());

        Product product = new Product();
        product.setId(purchaseItem.getId());
        purchaseItem.setProductId(product);

        return purchaseItem;
    }
}
