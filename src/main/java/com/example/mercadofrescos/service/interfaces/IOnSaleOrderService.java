package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.purchase.PurchaseItemResponseDTO;
import com.example.mercadofrescos.dto.purchase.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.purchase.PurchasePriceDTO;
import com.example.mercadofrescos.model.PurchaseOrder;
import com.example.mercadofrescos.model.enums.StatusOrder;

import java.util.List;

public interface IOnSaleOrderService {
    PurchaseOrder findById(Long id);
    PurchaseOrderRequestDTO updateOrderStatus(StatusOrder updateStatus, Long id);
    PurchasePriceDTO getCartOnSale(PurchaseOrder purchaseOrder);
    List<PurchaseItemResponseDTO> getOnSaleOrderById(Long id);
    PurchaseOrderRequestDTO updateOnSaleOrderStatus(StatusOrder purchaseOrder, Long id);
}
