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
     * @return lista de purchaseItem salva na base de dados
     */
    @Override
    public List<PurchaseItem> savePurchaseItemList(List<PurchaseItem> purchaseItems) {
        return this.repo.saveAll(purchaseItems);
    }
}
