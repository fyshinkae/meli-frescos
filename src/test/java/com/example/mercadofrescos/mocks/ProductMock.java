package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.BatchStock;
import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ProductMock {
    public static Product productTest () {
      Product product = new Product();

      Set<BatchStock> batch;
      batch = new HashSet<BatchStock>();
      BatchStock batch1 = new BatchStock();
      batch1.setId(1L);
      batch1.setProductQuantity(100);
      batch1.setDueDate(LocalDate.parse("2023-10-20", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
      batch.add(batch1);

      BigDecimal price = new BigDecimal(100.0);

      product.setId(1L);
      product.setSeller(UserSellerMock.sellerTest());
      product.setBatches(batch);
      product.setName("product");
      product.setPrice(price);
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
