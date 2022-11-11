package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.PurchaseItem;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseOrderDTO {

    @JsonProperty("totalPrice")
    private Double totalPrice;

    private List<PurchaseItem> purchaseItemList;

}
