package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.repository.IWarehouseRepo;
import com.example.mercadofrescos.service.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final IWarehouseRepo repo;

    /**
     * Busca uma Warehouse ou lança um erro caso não encontre
     * @author Theus
     * @param id da Warehouse
     */
    @Override
    public Warehouse findById(Long id) {
        Optional<Warehouse> warehouse = repo.findById(id);

        return warehouse.orElseThrow(() -> new NotFoundException("Warehouse not found"));
    }
}
