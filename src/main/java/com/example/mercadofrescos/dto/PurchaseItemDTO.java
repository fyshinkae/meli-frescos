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
        product.setId(purchaseItemDTO.getProductId());
        purchaseItem.setProduct(product);

        return purchaseItem;
    }

    /**
     * Converte PurchaseItem do modelo de banco para DTO
     * @author Giovanna
     * @param item item do modelo de banco a ser convertido
     * @return objeto do tipo PurchaseItemDTO convertido
     */
    public static PurchaseItemDTO convert(PurchaseItem item) {
        PurchaseItemDTO response = new PurchaseItemDTO();
        response.setProductId(item.getProduct().getId());
        response.setQuantity(item.getProductQuantity());

        return response;
    }

}
