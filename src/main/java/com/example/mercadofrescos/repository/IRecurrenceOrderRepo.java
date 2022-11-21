package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.RecurrenceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecurrenceOrderRepo extends JpaRepository<RecurrenceOrder, Long> {
}
