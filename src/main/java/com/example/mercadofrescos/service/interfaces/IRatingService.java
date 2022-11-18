package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.dto.rating.RatingDTO;
import com.example.mercadofrescos.model.Rating;

public interface IRatingService {

    RatingDTO createRating(Rating rating);
}
