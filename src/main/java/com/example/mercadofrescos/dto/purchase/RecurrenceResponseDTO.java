package com.example.mercadofrescos.dto.purchase;

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
    private PurchaseRequestDTO purchaseOrder;


    public RecurrenceResponseDTO(RecurrenceOrder recurrenceOrder) {
        this.nextPurchase = recurrenceOrder.getNextPurchase();
        this.setPurchaseOrder(PurchaseRequestDTO.convert(recurrenceOrder.getPurchaseOrder()));
    }
}
