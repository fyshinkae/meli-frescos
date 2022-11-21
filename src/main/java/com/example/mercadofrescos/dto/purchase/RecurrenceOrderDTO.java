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
public class RecurrenceOrderDTO {
    private Long orderId;
    private LocalDate nextPurchase;
    private PurchaseRequestDTO purchaseOrder;

    public RecurrenceOrderDTO(RecurrenceOrder recurrenceOrder) {
        this.setOrderId(recurrenceOrder.getId());
        this.setNextPurchase(recurrenceOrder.getNextPurchase());
        this.setPurchaseOrder(PurchaseRequestDTO.convert(recurrenceOrder.getPurchaseOrder()));
    }
}
