package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.Warehouse;

public interface IWarehouseService {
    Warehouse findById(Long id);
}
