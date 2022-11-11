package com.example.mercadofrescos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePriceDTO {
    @JsonProperty("totalPrice")
    private Double totalPrice;
}
