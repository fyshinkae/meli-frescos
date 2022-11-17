package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
        return PurchaseRequestDTO.convert(purchaseOrderRequestDTO.getPurchaseOrder());
    }

    /**
     * Converte PurchaseOrder do modelo de banco para DTO
     * @author Giovanna
     * @param purchaseOrder objeto de modelo de banco a ser convertido
     * @return objeto do tipo PurchaseOrderRequestDTO convertido
     */
    public static PurchaseOrderRequestDTO convert(PurchaseOrder purchaseOrder){
        PurchaseOrderRequestDTO response = new PurchaseOrderRequestDTO();
        response.setPurchaseOrder(PurchaseRequestDTO.convert(purchaseOrder));

        return response;
    }

}
