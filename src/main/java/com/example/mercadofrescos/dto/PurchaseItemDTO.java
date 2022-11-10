package com.example.mercadofrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemDTO {

    private Long productId;

    private Integer quantity;
}
