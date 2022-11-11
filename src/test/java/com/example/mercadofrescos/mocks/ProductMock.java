package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.enums.Category;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductMock {
    public static Product productTest () {
      Product product = new Product();

      Set<BatchStock> batch;
      batch = new HashSet<BatchStock>();

      BigDecimal price = new BigDecimal(100.0);

      product.setId(1L);
      product.setSeller(UserSellerMock.sellerTest());
      product.setBatches(batch);
      product.setName("product");
      product.setPrice(price);
      product.setCategory(Category.FRESH);
      return product;
    }
}
