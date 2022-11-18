package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long productId;

    @NotNull
    @DecimalMin(value = "5")
    @DecimalMax(value = "1")
    private BigDecimal rating;

    /**
     * Cria um DTO a partir de um modelo de banco
     * @author Gabriel
     * @param rating Objeto do modelo de banco a ser convertido a DTO
     * @return Um objeto Rating do tipo DTO
     */
    public RatingDTO(Rating rating){
        this.productId = rating.getId().getProductId();
        this.customerId = rating.getId().getCustomerId();
        this.rating = rating.getRating();
    }

    /**
     * Converte um tipo DTO a um modelo de banco
     * @author Gabriel
     * @param ratingDTO Objeto do tipo DTO a ser convertido ao modelo de Banco
     * @return Um objeto Rating do modelo de banco
     */
    public static Rating convert(RatingDTO ratingDTO) {
        Rating response = new Rating();

        CustomerProductId id = new CustomerProductId();
        id.setCustomerId(ratingDTO.getCustomerId());
        id.setProductId(ratingDTO.getProductId());

        response.setRating(ratingDTO.getRating());
        response.setId(id);

        response.setCreatedAt(LocalDateTime.now());

        return response;
    }

    /**
     * Converte um objeto do modelo de banco a um DTO
     * @author Gabriel
     * @param rating Objeto do modelo de banco a ser convertido a DTO
     * @return Um objeto Rating do tipo DTO
     */
    public static RatingDTO convert(Rating rating){
        return new RatingDTO(rating);
    }
}
