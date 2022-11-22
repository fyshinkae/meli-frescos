package com.example.mercadofrescos.dto.shipping;

import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.enums.StatusShipping;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShippingRequestDTO {
    @NotNull(message = "Shipping status can not be null")
    private StatusShipping statusShipping;

    @NotNull(message = "Shipping description can not be null")
    private String description;
}
