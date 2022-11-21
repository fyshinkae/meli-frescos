package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.RecurrenceOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurrenceResponseDTO {
    private LocalDate nextPurchase;
    // private PurchaseOrder purchaseOrder;


    public RecurrenceResponseDTO(RecurrenceOrder order) {
        this.nextPurchase = order.getNextPurchase();
        // this.purchaseOrder = order.getPurchaseOrder();
    }
}
