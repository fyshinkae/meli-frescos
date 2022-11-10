package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.enums.StatusOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDTO {
    private LocalDate date;

    private Long id;

    private StatusOrder orderStatus;

    private List<PurchaseItemDTO> products;
}
