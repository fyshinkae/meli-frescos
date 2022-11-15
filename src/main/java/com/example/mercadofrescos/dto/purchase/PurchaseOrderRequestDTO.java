package com.example.mercadofrescos.dto.purchase;

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

    public static PurchaseOrderRequestDTO convert(PurchaseOrder purchaseOrder){
        PurchaseOrderRequestDTO response = new PurchaseOrderRequestDTO();
        response.setPurchaseOrder(PurchaseRequestDTO.convert(purchaseOrder));
        return response;
    }

    /**
     * Converte um PurchaseRequestDTO para um objeto PurchaseOrder com reserva
     * @author Theus
     * @param purchase DTO a ser convertido
     * @return um objeto do tipo PurchaseOrder
     */
    public static PurchaseOrder convertToReservation(PurchaseOrderRequestDTO purchase) {
        PurchaseOrder purchaseOrder = PurchaseOrderRequestDTO.convert(purchase);
        purchaseOrder.setReservation(true);

        return purchaseOrder;
    }

}
