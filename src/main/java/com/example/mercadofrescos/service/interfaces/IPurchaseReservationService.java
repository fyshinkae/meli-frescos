package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseReservationResponseDTO;
import com.example.mercadofrescos.dto.purchase.ReservationAvailabilityResponseDTO;
import com.example.mercadofrescos.model.PurchaseOrder;

import java.util.List;


public interface IPurchaseReservationService {
    PurchaseReservationResponseDTO createReservation(PurchaseOrder purchaseReservation);
    List<PurchaseRequestDTO> findAll();
    PurchaseReservationResponseDTO findById(Long id);
    void deleteById(Long id);
    void verifyAvailability(Long id);
    PurchaseOrderRequestDTO finishReservation(Long id);
}
