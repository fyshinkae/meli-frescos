package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingByProductDTO {

    private Long productId;
    private BigDecimal averageRating;
    private List<RatingUserDTO> ratings;

    /**
     * Cria um DTO com base em uma lista de objetos do modelo de banco
     * @author Gabriel
     * @param ratings uma lista de objetos do modelo de banco
     */
    public RatingByProductDTO(List<Rating> ratings){
        this.averageRating = new BigDecimal(0);
        this.ratings = new ArrayList<>();

        if(!ratings.isEmpty()){
            Product firstProduct = ratings.get(0).getProduct();
            this.productId = firstProduct.getId();
            this.ratings = RatingUserDTO.convert(ratings);
            this.averageRating = RatingByProductDTO.getRatingAverage(this.ratings);
        }
    }

    /**
     * Cria um DTO a partir de um objeto de modelo de banco
     * @author Gabriel
     * @param rating um objeto de modelo de banco
     */
    public RatingByProductDTO(Rating rating) {
        this.productId = rating.getId().getProductId();
        this.averageRating = rating.getRating();

        List<RatingUserDTO> ratings = new ArrayList<>();
        ratings.add(RatingUserDTO.convert(rating));
        this.ratings = ratings;
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
     * Converte um objeto de modelo de banco para um DTO
     * @author Gabriel
     * @param rating um objeto de modelo de banco
     * @return um objeto DTO
     */
    public static RatingByProductDTO convert(Rating rating){
       return new RatingByProductDTO(rating);
    }

    /**
     * Calcula a média das avaliações a partir de uma lista
     * @author Gabriel
     * @param ratings lista de avaliações
     * @return a média das avaliações de uma lista
     */
    public static BigDecimal getRatingAverage(List<RatingUserDTO> ratings) {
        BigDecimal sum = new BigDecimal(0);

        for(RatingUserDTO rating : ratings){
            sum  = sum.add(rating.getRating());
        }

        return sum.divide(new BigDecimal(ratings.size()), 2, RoundingMode.HALF_UP);
    }

    /**
     * Realiza o merge entre o DTO e o rating de modelo de banco
     * @author Gabriel
     * @param ratingDTO DTO original
     * @param rating a avaliação a ser mergeada ao DTO
     * @return Um DTO com a avaliação de modelo de banco mergeada
     */
    public static RatingByProductDTO merge(RatingByProductDTO ratingDTO, Rating rating) {
        RatingByProductDTO response = new RatingByProductDTO();

        List<RatingUserDTO> ratings = ratingDTO.getRatings();
        ratings.add(RatingUserDTO.convert(rating));

        response.setProductId(rating.getId().getProductId());
        response.setAverageRating(RatingByProductDTO.getRatingAverage(ratings));
        response.setRatings(ratings);

        return response;
    }
}
