package com.example.mercadofrescos.dto.product;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseProductsDTO {

    private Long warehouseCode;

    private int totalQuantity;

    /**
     * Cria um WarehouseProductsDTO a partir de um armazém e seu respectivo produto
     * @author Anderson e Gabriel
     * @param warehouse armazém a ser convertida
     * @param product Produto a ser convertido
     */
    public WarehouseProductsDTO(Warehouse warehouse, Product product) {
        this.warehouseCode = warehouse.getId();
        Set<BatchStock> batchStocks = product.getBatches().stream().filter(batchStock -> batchStock.getInboundOrder()
                .getSection()
                .getWarehouse()
                .getId() == warehouse.getId()).collect(Collectors.toSet());

        for(BatchStock batchStock: batchStocks) {
            this.totalQuantity += batchStock.getProductQuantity();
        }
    }
}
