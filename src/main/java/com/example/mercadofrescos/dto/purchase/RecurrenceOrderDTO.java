package com.example.mercadofrescos.dto.purchase;

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
    private Long dayOfMonth;
    private LocalDate createdAt;
    private PurchaseRequestDTO purchaseOrder;

    public RecurrenceOrderDTO(RecurrenceOrder recurrenceOrder) {
        this.setOrderId(recurrenceOrder.getId());
        this.setCreatedAt(recurrenceOrder.getCreatedAt());
        this.setDayOfMonth(recurrenceOrder.getDayOfMonth());
        this.setPurchaseOrder(PurchaseRequestDTO.convert(recurrenceOrder.getPurchaseOrder()));
    }
}
