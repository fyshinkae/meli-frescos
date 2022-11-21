package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.enums.Reputation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingBySellerDTO {
    private Long sellerId;
    private Reputation level;
    private List<RatingByProductDTO> products;

    /**
     * Cria um DTO a partir de um objeto de modelo de banco
     * @author Gabriel
     * @param ratings uma lista de avaliações de modelo de banco
     * @param sellerId Id do vendedor
     * @param level reputação do vendedor
     */
    public RatingBySellerDTO(List<Rating> ratings, Long sellerId, Reputation level){
        this.sellerId = sellerId;
        this.level = level;
        this.initializeProducts(ratings);
    }

    /**
     * Converte um objeto de modelo de banco para um objeto DTO
     * @author Gabriel
     * @param ratings uma lista de avaliações de modelo de banco
     * @param sellerId Id do vendedor
     * @param level reputação do vendedor
     * @return um objeto DTO
     */
    public static RatingBySellerDTO convert(List<Rating> ratings, Long sellerId, Reputation level){
        return new RatingBySellerDTO(ratings, sellerId, level);
    }

    /**
     * Inicializa o HashMap de produtos
     * @author Gabriel
     * @param ratings uma lista de avaliações
     */
    private void initializeProducts(List<Rating> ratings){
        HashMap<Long, RatingByProductDTO> productsHashMap = new HashMap<>();
        this.products = new ArrayList<>();
        for(Rating rating : ratings){
            Long productId = rating.getId().getProductId();
            if(productsHashMap.containsKey(productId)){
                RatingByProductDTO ratingByProductDTO = productsHashMap.get(productId);
                productsHashMap.put(productId, RatingByProductDTO.merge(ratingByProductDTO, rating));
            } else {
                productsHashMap.put(productId, RatingByProductDTO.convert(rating));
            }
        }

        this.products = new ArrayList<>(productsHashMap.values());
    }
}
