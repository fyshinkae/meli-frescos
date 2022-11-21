package com.example.mercadofrescos.dto.purchase;

import com.example.mercadofrescos.model.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckAvailabilityResponseDTO {

    private Long id, buyerId;

    private BigDecimal totalPrice;

    private List<AvailabilityDTO> items;

    public CheckAvailabilityResponseDTO(PurchaseOrder purchase) {
        this.id = purchase.getId();
        this.buyerId = purchase.getCustomer().getId();
        this.totalPrice = purchase.getItemList()
                .stream()
                .map(itemList -> itemList.getProduct().getPrice().multiply(BigDecimal.valueOf(itemList.getProductQuantity())))
                .reduce(BigDecimal::add).get();
        this.items = purchase.getItemList()
                .stream()
                .map(AvailabilityDTO::new)
                .collect(Collectors.toList());
    }
}
