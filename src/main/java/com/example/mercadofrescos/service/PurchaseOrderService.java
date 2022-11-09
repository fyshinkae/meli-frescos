package com.example.mercadofrescos.service;

import com.example.mercadofrescos.model.InboundOrder;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final IPurchaseOrderRepo repo;

    public Double getPurchaseItems(PurchaseOrder purchaseOrder) {


    }

    public Double getTotalPrice(InboundOrder inboundOrder) {
        double totalPrice = 0;




        return totalPrice;
    }


}
