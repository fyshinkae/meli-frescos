package com.example.mercadofrescos.service;

import com.example.mercadofrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final IPurchaseOrderRepo repo;



}
