package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.rating.RatingByProductDTO;
import com.example.mercadofrescos.dto.rating.RatingBySellerDTO;
import com.example.mercadofrescos.dto.rating.RatingByUserDTO;
import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.model.Rating;
import com.example.mercadofrescos.model.enums.Reputation;

import java.util.List;

public interface IRatingService {

    RatingDTO createRating(Rating rating);

    RatingByUserDTO getRatingByUser(Long customerId);

    RatingByProductDTO getRatingByProduct(Long productId);

    List<RatingBySellerDTO> getRatingBySeller(Reputation reputation);
}
