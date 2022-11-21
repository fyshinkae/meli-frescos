package com.example.mercadofrescos.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecurrenceOrderDTO {
    private Long orderId;
    // private LocalDate nextPurchase;
}
