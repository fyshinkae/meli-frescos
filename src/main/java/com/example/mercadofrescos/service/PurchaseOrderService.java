package com.example.mercadofrescos.service;

import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import com.example.mercadofrescos.dto.PurchaseItemDTO;
import com.example.mercadofrescos.dto.PurchaseOrderRequestDTO;
import com.example.mercadofrescos.dto.PurchasePriceDTO;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IBatchStockRepo repo;
    private final IProductService productService;


    @Override
    public PurchasePriceDTO getCartAmount(PurchaseOrderRequestDTO purchaseOrder) {
        int availableQuantity;
        double totalCartAmount = 0d;
        BigDecimal singleCartAmount;

        List<PurchaseItemDTO> purchaseItemList = purchaseOrder.getPurchaseOrder().getProducts();

        for (PurchaseItemDTO item : purchaseItemList) {
            Product product = productService.findById(item.getProductId());
            Set<BatchStock> batches = product.getBatches();
            BatchStock batchStock = batches.iterator().next();
            Product productPrice = batchStock.getProduct();
            if (item.getQuantity() > batchStock.getProductQuantity()) {
                throw new RuntimeException("Deu ruim!");
            }
            singleCartAmount = productPrice.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setQuantity(batchStock.getProductQuantity());

            totalCartAmount = singleCartAmount.add(BigDecimal.valueOf(totalCartAmount)).doubleValue();
            //availableQuantity = batchStock.getProductQuantity() - item.getQuantity();
            //batchStock.setProductQuantity(availableQuantity);
            //item.setProductId(batchStock.getId());
            //repo.save(batchStock);
        }
        return new PurchasePriceDTO(totalCartAmount);
    }


}
