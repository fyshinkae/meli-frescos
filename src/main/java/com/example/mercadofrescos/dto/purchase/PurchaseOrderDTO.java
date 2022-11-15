package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
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
