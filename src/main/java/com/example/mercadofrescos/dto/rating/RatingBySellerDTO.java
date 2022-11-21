package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.enums.Reputation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingBySellerDTO {
    private Long sellerId;
    private BigDecimal averageRating;
    private Reputation level;
    private HashMap<Long, RatingByProductDTO> products;

    /**
     * @author Gabriel
     * @param ratings
     * @param sellerId
     * @param level
     */
    public RatingBySellerDTO(List<Rating> ratings, Long sellerId, Reputation level){
        this.sellerId = sellerId;
        this.averageRating = new BigDecimal(0);
        this.level = level;
        this.initializeProducts(ratings);
    }

    /**
     *
     * @author Gabriel
     * @param ratings
     * @param sellerId
     * @param level
     * @return
     */
    public static RatingBySellerDTO convert(List<Rating> ratings, Long sellerId, Reputation level){
        return new RatingBySellerDTO(ratings, sellerId, level);
    }

    /**
     * @author Gabriel
     * @param ratings
     */
    private void initializeProducts(List<Rating> ratings){
        this.products = new HashMap<>();
        for(Rating rating : ratings){
            Long productId = rating.getId().getProductId();
            if(this.products.containsKey(productId)){
                RatingByProductDTO ratingByProductDTO = this.products.get(productId);
                this.products.put(productId, RatingByProductDTO.merge(ratingByProductDTO, rating));
            } else {
                this.products.put(productId, RatingByProductDTO.convert(rating));
            }
        }
    }
}
