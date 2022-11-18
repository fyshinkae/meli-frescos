package com.example.mercadofrescos.dto.rating;

import com.example.mercadofrescos.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingByUserDTO {
    private Long customerId;
    private List<RatingProductDTO> ratings;

    public RatingByUserDTO(List<Rating> ratings){
        if(!this.ratings.isEmpty()){
            this.customerId = ratings.get(0).getId().getCustomerId();
            this.ratings = RatingProductDTO.convert(ratings);
        }
    }

    public static RatingByUserDTO convert(List<Rating> ratings){
        return new RatingByUserDTO(ratings);
    }
}
