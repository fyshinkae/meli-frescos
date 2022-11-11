package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Product;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.Warehouse;
import com.example.mercadofrescos.model.enums.Role;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserAgentMock {
    public static User agentTest () {
        User agent = new User();
        Set<Product> productList;
        productList = new HashSet<Product>();
        Warehouse warehouse = new Warehouse();

        agent.setId(1L);
        agent.setName("agent");
        agent.setEmail("agent@email.com");
        agent.setPassword("password");
        agent.setRole(Role.AGENT);
        agent.setProducts(productList);
        agent.setWarehouse(warehouse);
        agent.setOrder(new ArrayList<>());
        return agent;
    }
}
