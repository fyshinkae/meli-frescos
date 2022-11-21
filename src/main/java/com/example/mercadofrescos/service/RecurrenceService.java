package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.purchase.RecurrenceOrderDTO;
import com.example.mercadofrescos.dto.purchase.RecurrenceResponseDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.RecurrenceOrder;
import com.example.mercadofrescos.repository.IRecurrenceOrderRepo;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import com.example.mercadofrescos.service.interfaces.IRecurrenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecurrenceService implements IRecurrenceService {

    private final IRecurrenceOrderRepo repo;

    private final IPurchaseOrderService purchaseOrderService;
    @Override
    public RecurrenceResponseDTO createRecurrence(RecurrenceOrderDTO recurrenceOrder) {
        PurchaseOrder purchaseOrder = this.purchaseOrderService.findById(recurrenceOrder.getOrderId());

        RecurrenceOrder order = new RecurrenceOrder();
        LocalDate date = LocalDate.from(purchaseOrder.getDate()).plusMonths(1);
        order.setNextPurchase(date);
        order.setPurchaseOrder(purchaseOrder);

        this.repo.save(order);
        return new RecurrenceResponseDTO(order);
    }

    @Override
    public List<RecurrenceOrderDTO> getAllRecurrences() {
        List<RecurrenceOrderDTO> result = this.repo.findAll().stream().map(RecurrenceOrderDTO::new).collect(Collectors.toList());

        if (result.isEmpty()) throw new NotFoundException("There are no registered recurrences");
        return result;
    }

    @Override
    public void deleteByID(Long id) {
        this.repo.deleteById(id);
    }
}
