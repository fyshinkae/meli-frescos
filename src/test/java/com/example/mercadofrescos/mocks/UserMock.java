package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.model.enums.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class UserMock {
    public static User userTest () {
        User user = new User();
        Set<Product> productList;
        productList = new HashSet<Product>();
        Warehouse warehouse = new Warehouse();

        user.setId(1L);
        user.setName("user");
        user.setEmail("user@email.com");
        user.setPassword("password");
        user.setRole(Role.CUSTOMER);
        user.setProducts(productList);
        user.setWarehouse(warehouse);
        user.setOrder(new ArrayList<>());
        return user;
    }
}
