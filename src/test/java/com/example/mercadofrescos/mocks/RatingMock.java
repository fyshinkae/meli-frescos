package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.CustomerProductId;
import com.example.mercadofrescos.model.Rating;

import java.math.BigDecimal;

public class RatingMock {

    public static Rating generateRatingMock(){
        Rating mock = new Rating();
        CustomerProductId id = RatingMock.generateRatingId(1L,1L);

        mock.setId(id);
        mock.setRating(new BigDecimal(5));

        return mock;
    }

    public static CustomerProductId generateRatingId(Long customerId, Long productId){
        CustomerProductId id = new CustomerProductId();

        id.setProductId(customerId);
        id.setCustomerId(productId);

        return id;
    }
}
