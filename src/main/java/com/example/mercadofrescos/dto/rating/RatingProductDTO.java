package com.example.mercadofrescos.dto.rating;

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
public class RatingProductDTO {
    private Long productId;
    private String productName;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RatingProductDTO(Rating rating) {
        this.productId = rating.getId().getProductId();
        this.rating = rating.getRating();
        this.createdAt = rating.getCreatedAt();
        this.updatedAt = rating.getUpdatedAt();

        if(rating.getProduct() != null){
            this.productName = rating.getProduct().getName();
        }
    }

    public static RatingProductDTO convert(Rating rating){
        return new RatingProductDTO(rating);
    }

    public static List<RatingProductDTO> convert(List<Rating> ratings) {
        List<RatingProductDTO> response = new ArrayList<>();
        for(Rating rating: ratings){
            response.add(RatingProductDTO.convert(rating));
        }
        return response;
    }
}
