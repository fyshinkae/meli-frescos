package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.model.enums.StatusOrder;
import com.example.mercadofrescos.model.enums.StatusShipping;
import com.example.mercadofrescos.repository.ITrakingOrderRepo;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.ITrackingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TrackingOrderService implements ITrackingOrderService {

    private final ITrakingOrderRepo repo;
    private final IPurchaseOrderService orderService;

    @Override
    public TrackingOrderResponseDTO create(TrackingOrderRequestDTO trackingOrder) {

        TrackingOrder newTracking = new TrackingOrder();

        Shipping shipping = new Shipping();
        newTracking.setShipping(shipping);

        newTracking.setTrackingDate(LocalDateTime.now());

        PurchaseOrder purchaseOrder = orderService.findById(trackingOrder.getPurchaseOrderId());
        newTracking.setPurchaseOrder(purchaseOrder);

        Address address = purchaseOrder.getCustomer().getAddress();
        newTracking.setAddress(address);

        return TrackingOrderResponseDTO.converter(repo.save(newTracking));
    }

    @Override
    @Transactional
    public TrackingOrderResponseDTO update(Long id, TrackingOrder trackingOrder) {
        TrackingOrder trackingOrderById = this.repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Does not exits Appointment"));

        if (trackingOrderById.getShipping().getStatusShipping() != StatusShipping.PREPARING) {
            throw new RuntimeException("Order cannot be updated");
        }

        trackingOrderById.setTrackingDate(trackingOrder.getTrackingDate());
        return TrackingOrderResponseDTO.converter(repo.save(trackingOrderById));
    }

//    @Override
//    public void validatorStatus(PurchaseOrder purchaseOrder) {
//        TrackingOrder trackingOrder = repo.findTrackingOrderByPurchaseOrderId(purchaseOrder.getId());
//
//        if (purchaseOrder.getStatusOrder() == StatusOrder.FINALIZADO) {
//            trackingOrder.getShipping().setStatusShipping(StatusShipping.CLOSED);
//            repo.save(trackingOrder);
//        }
//    }
}
