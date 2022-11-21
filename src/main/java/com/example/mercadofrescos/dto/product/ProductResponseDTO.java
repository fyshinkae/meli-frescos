package com.example.mercadofrescos.dto.product;

import com.example.mercadofrescos.dto.purchase.SellerResponseDTO;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private String name;

    private Category category;

    private BigDecimal price;

    private SellerResponseDTO seller;

    /**
     * Converte o modelo product de banco para um DTO
     * @author Gabriel
     * @param product objeto a ser convertido para dto
     */
    public ProductResponseDTO(Product product){
        this.category = product.getCategory();
        this.name = product.getName();
        this.price = product.getPrice();
        this.seller = new SellerResponseDTO(product.getSeller());
    }
}
