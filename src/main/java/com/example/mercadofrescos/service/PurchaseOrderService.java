package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.BatchStockDTO;
import com.example.mercadofrescos.model.*;
import com.example.mercadofrescos.repository.IBatchStockRepo;
import com.example.mercadofrescos.repository.IProductRepo;
import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final IBatchStockRepo repo;

    public Double getPurchaseItems(PurchaseOrder purchaseOrder) {


    }

    public Double getCartAmount(<List<PurchaseItem> purchaseItemList) {
        int availableQuantity = 0;
        double totalCartAmount = 0d;
        BigDecimal singleCartAmount;

        for (PurchaseItem item : purchaseItemList) {

            Product productId = item.getProductId();
            Optional<BatchStock> product = repo.findById(item.getId());
            if (product.isPresent()) {
                BatchStock product1 = product.get();
                Product productPrice = product.get().getProduct();
                if (product1.getProductQuantity() < item.getProductQuantity()) {
                   singleCartAmount = productPrice.getPrice().multiply(BigDecimal.valueOf(product1.getProductQuantity()));
                    item.setProductQuantity(product1.getProductQuantity());
                } else {
                    singleCartAmount = productPrice.getPrice().multiply(BigDecimal.valueOf(item.getProductQuantity()));
                    availableQuantity = product1.getProductQuantity() - item.getProductQuantity();
                }
                // totalCartAmount = total
                        // totalCartAmount + singleCartAmount;
            }
        }





    }


}
