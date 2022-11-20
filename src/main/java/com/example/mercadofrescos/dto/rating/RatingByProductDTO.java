package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingByProductDTO {

    private Long productId;
    private BigDecimal average;
    private List<RatingUserDTO> ratings;

    /**
     * Cria um DTO com base em uma lista de objetos do modelo de banco
     * @author Gabriel
     * @param ratings uma lista de objetos do modelo de banco
     */
    public RatingByProductDTO(List<Rating> ratings){
        this.average = new BigDecimal(0);
        this.ratings = new ArrayList<>();

        if(!this.ratings.isEmpty()){
            Product firstProduct = ratings.get(0).getProduct();
            this.productId = firstProduct.getId();
            this.ratings = RatingUserDTO.convert(ratings);
            this.average = this.getRatingAverage(this.ratings);
        }
    }

    /**
     * Converte uma lista de objetos de modelo de banco para um DTO
     * @author Gabriel
     * @param ratings uma lista de objetos de modelo de banco
     * @return um objeto DTO
     */
    public static RatingByProductDTO convert(List<Rating> ratings) {
        return new RatingByProductDTO(ratings);
    }

    /**
     * Calcula a média das avaliações a partir de uma lista
     * @author Gabriel
     * @param ratings lista de avaliações
     * @return a média da avaliações de uma lista
     */
    private BigDecimal getRatingAverage(List<RatingUserDTO> ratings) {
        BigDecimal sum = new BigDecimal(0);

        for(RatingUserDTO rating : ratings){
            sum  = sum.add(rating.getRating());
        }

        return sum.divide(new BigDecimal(ratings.size()));
    }
}
