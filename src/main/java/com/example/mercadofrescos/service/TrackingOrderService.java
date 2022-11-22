package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.exception.InvalidCreateTrackingException;
import com.example.mercadofrescos.exception.NotFoundException;
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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrackingOrderService implements ITrackingOrderService {

    private final ITrakingOrderRepo repo;
    private final IPurchaseOrderService orderService;

    @Override
    public TrackingOrderResponseDTO create(TrackingOrderRequestDTO trackingOrder) {
        TrackingOrder validator = repo.findTrackingOrderByPurchaseOrderId(trackingOrder.getPurchaseOrderId());

        if (validator != null) {
            throw new InvalidCreateTrackingException("Tracking order already exists");
        }

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
    public TrackingOrder findTrackingOrderById(Long id) {
        return this.repo.findById(id).orElseThrow(
                () -> new NotFoundException("Tracking order not found"));
    }

    @Override
    public List<TrackingOrder> findAllTrackingOrder() {
        List<TrackingOrder> list = repo.findAll();

        if (list.isEmpty()){
            throw new NotFoundException("There is no Tracking Order list");
        }

        return list;
    }
}
