package com.example.mercadofrescos.model.enums;

import java.math.BigDecimal;

public enum Reputation {
    GREEN(10, new BigDecimal(4.5)),
    YELLOW(5, new BigDecimal(4)),
    RED(0, new BigDecimal(0));

    private final int minimumNumberOfRatings;
    private final BigDecimal minimumAverageRating;

    Reputation(int minimumNumberOfRatings, BigDecimal minimumAverageRating) {
        this.minimumNumberOfRatings = minimumNumberOfRatings;
        this.minimumAverageRating = minimumAverageRating;
    }

    public int getMinimumNumberOfRatings() {
        return minimumNumberOfRatings;
    }

    public BigDecimal getMinimumAverageRating() {
        return minimumAverageRating;
    }
}
