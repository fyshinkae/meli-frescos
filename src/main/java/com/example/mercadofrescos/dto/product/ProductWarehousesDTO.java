package com.example.mercadofrescos.dto.product;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductWarehousesDTO {

    private Long productId;

    private List<WarehouseProductsDTO> warehouses;

    /**
     * Cria um ProductWarehousesDTO a partir da lista de armazéns e seu respectivo produto
     * @author Anderson e Gabriel
     * @param product Produto a ser convertido
     * @param warehouses lista de armazéns a ser convertida
     */
    public ProductWarehousesDTO(Product product, Set<Warehouse> warehouses) {
        this.productId = product.getId();

        this.warehouses = new ArrayList<>();
        for(Warehouse warehouse: warehouses) {
            this.warehouses.add(new WarehouseProductsDTO(warehouse, product));
        }
    }
}
