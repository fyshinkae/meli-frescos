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
      User seller = new User();
      Set<BatchStock> batch;
      batch = new HashSet<BatchStock>();

      product.setId(1L);
      product.setSeller(seller);
      product.setBatches(batch);
      product.setName("product");
      product.setPrice(new BigDecimal(100.0));
      product.setCategory(Category.FRESH);
      return product;
    }

    public static Product getProductFrozen() {
        Product product = ProductMock.productTest();
        product.setCategory(Category.FROZEN);

        return product;
    }

    public static Product getProductRefrigerated() {
        Product product = ProductMock.productTest();
        product.setCategory(Category.REFRIGERATED);

        return product;
    }

    public static Product getProductFresh() {
        Product product = ProductMock.productTest();
        product.setCategory(Category.FRESH);

        return product;
    }
}
