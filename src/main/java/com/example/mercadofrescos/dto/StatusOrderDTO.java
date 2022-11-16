package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.enums.StatusOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatusOrderDTO {

    private StatusOrder orderStatus;
}
