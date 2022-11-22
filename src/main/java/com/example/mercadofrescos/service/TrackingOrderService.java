package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.tracking.TrackingOrderRequestDTO;
import com.example.mercadofrescos.dto.tracking.TrackingOrderResponseDTO;
import com.example.mercadofrescos.exception.InvalidCreateTrackingException;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.Address;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.Shipping;
import com.example.mercadofrescos.model.TrackingOrder;
import com.example.mercadofrescos.repository.ITrakingOrderRepo;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.ITrackingOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackingOrderService implements ITrackingOrderService {

    private final ITrakingOrderRepo repo;
    private final IPurchaseOrderService orderService;

    /**
     * Metodo de criação de trackingOrder
     * @author Felipe Shinkae
     * @param trackingOrder ojbeto para criação do TrackingORder
     * @return DTO do objeto criado
     */
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

    /**
     * metodo para retornar trackingOrder pela id
     * @author Felipe Shinkae
     * @param id trackingOrder id
     * @return retorna trackingOrder conforme o id
     */
    @Override
    public TrackingOrder findTrackingOrderById(Long id) {
        return this.repo.findById(id).orElseThrow(
                () -> new NotFoundException("Tracking order not found"));
    }

    /**
     * Metodo para retornar todas as trackingOrders
     * @author Felipe Shinkae
     * @return Retorna todas as trackingOrders geradas
     */
    @Override
    public List<TrackingOrder> findAllTrackingOrder() {
        List<TrackingOrder> list = repo.findAll();

        if (list.isEmpty()){
            throw new NotFoundException("There is no Tracking Order list");
        }

        return list;
    }
}
