package com.example.mercadofrescos.dto;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWarehousesDTO {
    private Long productId;
    private List<WarehouseProductsDTO> warehouses;

    public ProductWarehousesDTO(Product product, List<Warehouse> warehouses) {
        this.productId = product.getId();

        for(Warehouse warehouse: warehouses) {
            this.warehouses.add(new WarehouseProductsDTO(warehouse, product));
        }
    }
}
