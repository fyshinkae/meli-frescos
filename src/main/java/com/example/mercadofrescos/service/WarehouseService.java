package com.example.mercadofrescos.service;

import com.example.mercadofrescos.dto.ProductWarehousesDTO;
import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.repository.IWarehouseRepo;
import com.example.mercadofrescos.service.interfaces.IProductService;
import com.example.mercadofrescos.service.interfaces.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final IWarehouseRepo repo;
    private final IProductService productService;

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

    /**
     * Obtém um produto e uma lista armazéns com suas devidas quantidades
     * @author Anderson e Gabriel
     * @param productId id do produto a ser pesquisado
     * @return Um produto com sua lista de armazéns
     */
    @Override
    public ProductWarehousesDTO getProductsQuantity(Long productId) {
        Product product = productService.findById(productId);
        Set<Warehouse> warehouses = repo.getWarehousesByProductId(productId);

        if(warehouses.isEmpty()){
            throw new NotFoundException("Warehouses for product " + product + " not found");
        }

        return new ProductWarehousesDTO(product, warehouses);
    }

}
