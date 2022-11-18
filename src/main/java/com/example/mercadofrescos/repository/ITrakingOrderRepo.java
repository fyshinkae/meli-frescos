package com.example.mercadofrescos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITrakingOrderRepo<Appointment> extends JpaRepository<Appointment, Long> {
}
