package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.model.enums.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserSellerMock {

    public static User sellerTest () {
        User seller = new User();
        Set<Product> productList;
        productList = new HashSet<Product>();
        Warehouse warehouse = new Warehouse();

        seller.setId(1L);
        seller.setName("seller");
        seller.setEmail("seller@email.com");
        seller.setPassword("password");
        seller.setRole(Role.SELLER);
        seller.setProducts(productList);
        seller.setWarehouse(warehouse);
        seller.setOrder(new ArrayList<>());
        return seller;
    }
}
