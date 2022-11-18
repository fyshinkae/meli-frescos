package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAddressRepo extends JpaRepository<Address, Long> {
}
