package com.example.mercadofrescos.service;

import com.example.mercadofrescos.model.PurchaseItem;
import com.example.mercadofrescos.repository.IPurchaseItemRepo;
import com.example.mercadofrescos.service.interfaces.IPurchaseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseItemService implements IPurchaseItemService {

    private final IPurchaseItemRepo repo;

    /**
     * Salva uma lista de PurchaseItem a ser salva na base de dados
     * @author Gabriel
     * @param purchaseItems uma lista de purchaseItem a ser salva na base de dados
     */
    public void savePurchaseItemList(List<PurchaseItem> purchaseItems) {
        this.repo.saveAll(purchaseItems);
    }
}
