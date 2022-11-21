package com.example.mercadofrescos.service;

import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.model.enums.StatusShipping;
import com.example.mercadofrescos.repository.ITrakingOrderRepo;
import com.example.mercadofrescos.service.interfaces.ITrackingOrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TrackingOrderService implements ITrackingOrderService {

    private final ITrakingOrderRepo repo;
    private final PurchaseOrderService orderService;

    @Override
    public List<TrackingOrder> findAllByPurchaseOrder(Long userId) {
        List<TrackingOrder> ordersByUserId = repo.findAllByPurchaseOrder_user_id(userId);

        if (ordersByUserId.isEmpty()) {
            throw new EntityNotFoundException("Tracking order not found");
        }
        return ordersByUserId;
    }

    @Override
    public List<TrackingOrder> findAllUser() {
        List<TrackingOrder> orders = repo.findAll();

        if (orders.isEmpty()) {
            throw new EntityNotFoundException("Tracking order not found");
        }
        return orders;

    }

    @Override
    public Optional<TrackingOrder> findUserById(Long id) {
        Optional<TrackingOrder> orderById = repo.findById(id);

        if (orderById.isEmpty()) {
            throw new EntityNotFoundException("Tracking order not found");
        }
        return orderById;
    }

    @Override
    public TrackingOrder create(TrackingOrder trackingOrder) {
        Shipping shipping = new Shipping();
        trackingOrder.setShipping(shipping);

        PurchaseOrder purchaseOrder = orderService.findById(trackingOrder.getPurchaseOrder().getId());
        Address address = purchaseOrder.getCustomer().getAddress();
        trackingOrder.setAddress(address);

        return repo.save(trackingOrder);
    };

    @Override
    @Transactional
    public TrackingOrder update(Long id, TrackingOrder trackingOrder) {
        TrackingOrder trackingOrderById = this.repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Does not exits Appointment"));

        if (trackingOrderById.getShipping().getStatusShipping() != StatusShipping.PREPARING) {
            throw new RuntimeException("Order cannot be updated");
        }

        Address address = trackingOrderById.getAddress();
        address.setNumber(trackingOrder.getAddress().getNumber());
        address.setStreet(trackingOrder.getAddress().getStreet());
        address.setRegion(trackingOrder.getAddress().getRegion());
        address.setZipcode(trackingOrder.getAddress().getZipcode());

        trackingOrderById.setTrackingDate(trackingOrder.getTrackingDate());
        trackingOrderById.setAddress(address);

        return repo.save(trackingOrderById);
    }
}
