package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.RecurrenceOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurrenceResponseDTO {
    private LocalDate createdAt;

    // @JsonFormat(pattern = "yyyy-MM-dd")
    private Long dayOfMonth;

    private PurchaseRequestDTO purchaseOrder;


    public RecurrenceResponseDTO(RecurrenceOrder recurrenceOrder) {
        this.createdAt = recurrenceOrder.getCreatedAt();
        this.dayOfMonth = recurrenceOrder.getDayOfMonth();
        this.setPurchaseOrder(PurchaseRequestDTO.convert(recurrenceOrder.getPurchaseOrder()));
    }
}
