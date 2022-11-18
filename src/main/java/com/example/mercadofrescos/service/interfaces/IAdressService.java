package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.Address;

public interface IAdressService {
    Address findAdressById(Long id);
    Address createAdress(Address address);
    Address updateAdress(Address address);
    void deleteAdress(Long id);
}
