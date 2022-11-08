package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInboundOrderRepo extends JpaRepository<InboundOrder, Long> {
}
