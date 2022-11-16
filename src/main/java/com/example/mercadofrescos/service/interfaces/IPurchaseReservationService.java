package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.model.PurchaseOrder;

public interface IPurchaseReservationService {
    PurchaseReservationResponseDTO createReservation(PurchaseOrder purchaseReservation);
}
