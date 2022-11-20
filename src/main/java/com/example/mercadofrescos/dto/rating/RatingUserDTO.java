package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingUserDTO {
    private Long customerId;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Cria um DTO a partir de um objeto de modelo de banco
     * @author Gabriel
     * @param rating Objeto de banco base para criar o DTO
     */
    public RatingUserDTO(Rating rating){
        this.customerId = rating.getId().getCustomerId();
        this.rating = rating.getRating();
        this.createdAt = rating.getCreatedAt();
        this.updatedAt = rating.getUpdatedAt();
    }

    /**
     * Converte um objeto do modelo de banco a um DTO
     * @author Gabriel
     * @param rating Objeto do modelo de banco a ser convertido a DTO
     * @return Um objeto Rating do tipo DTO
     */
    public static RatingUserDTO convert(Rating rating){
        return new RatingUserDTO(rating);
    }

    /**
     * Converte uma lista de objetos do modelo de banco em lista de DTOs
     * @author Gabriel
     * @param ratings Lista de objetos de modelo de banco
     * @return Uma lista de DTOs
     */
    public static List<RatingUserDTO> convert(List<Rating> ratings){
        List<RatingUserDTO> response = new ArrayList<>();

        for(Rating rating : ratings){
            response.add(RatingUserDTO.convert(rating));
        }

        return response;
    }

    /**
     * Converte um objeto DTO para modelo de banco
     * @author Gabriel
     * @param ratingUserDTO Objeto DTO a ser convertido para modelo de banco DTO
     * @param productId Id do produto no qual o ratingUserDTO está associado
     * @return Um objeto Rating do tipo modelo de banco
     */
    public static Rating convert(RatingUserDTO ratingUserDTO, Long productId){
        Rating response = new Rating();
        CustomerProductId id = new CustomerProductId();

        id.setCustomerId(ratingUserDTO.getCustomerId());
        id.setProductId(productId);

        response.setCreatedAt(ratingUserDTO.getCreatedAt());
        response.setUpdatedAt(ratingUserDTO.getUpdatedAt());
        response.setRating(ratingUserDTO.getRating());
        response.setId(id);

        return response;
    }
}
