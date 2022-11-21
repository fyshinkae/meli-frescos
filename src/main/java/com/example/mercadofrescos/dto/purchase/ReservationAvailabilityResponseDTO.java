package com.example.mercadofrescos.dto.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationAvailabilityResponseDTO extends PurchaseReservationResponseDTO {
    private String availability;
}
