package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReservationResponseDTO {
    private Long id;
    private LocalDateTime createdAt;
    private List<PurchaseItemResponseDTO> items;

    /**
     * Converte o modelo PurchaseOrder para um DTO
     * @author Theus
     * @param purchase objeto do modelo PurchaseOrder a ser convertido para dto
     */
    public PurchaseReservationResponseDTO(PurchaseOrder purchase) {
        this.id = purchase.getId();
        this.createdAt = LocalDateTime.now();
        this.items = purchase.getItemList().stream()
                .map(PurchaseItemResponseDTO::new)
                .collect(Collectors.toList());
    }
}
