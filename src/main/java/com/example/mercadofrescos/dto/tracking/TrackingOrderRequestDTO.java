package com.example.mercadofrescos.dto.tracking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TrackingOrderRequestDTO {
    @NotNull( message = "Purchase order can not be null")
    private Long purchaseOrderId;
}
