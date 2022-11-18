package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingRepo extends JpaRepository<Shipping, Long> {
}
