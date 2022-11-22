package com.example.mercadofrescos.mocks;

import com.example.mercadofrescos.model.Address;

public class AddressMock {
    public static Address addressTest() {
        Address address = new Address();
        address.setId(1L);
        address.setStreet("Rua Teste 1");
        address.setNumber("79");
        address.setZipcode("79600000");
        address.setRegion("Paulo Sao");

        return address;
    }
}
