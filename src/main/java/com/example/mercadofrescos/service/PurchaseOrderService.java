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
