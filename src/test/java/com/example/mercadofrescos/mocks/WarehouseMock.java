package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.model.Warehouse;

public class WarehouseMock {
    public static Warehouse warehouseTest () {
        Warehouse warehouse = new Warehouse();
        User agent = new User();

        warehouse.setId(1L);
        warehouse.setAgent(agent);
        warehouse.setName("warehouse");
        warehouse.setAddress("Brasil");
        return warehouse;
    }
}
