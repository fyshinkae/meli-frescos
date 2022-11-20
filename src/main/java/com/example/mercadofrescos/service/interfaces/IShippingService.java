package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.Shipping;

import java.util.List;

public interface IShippingService {
    Shipping update(Long id, Shipping shipping);
    List<Shipping> findAll();
}
