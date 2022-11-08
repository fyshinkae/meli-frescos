package com.example.mercadofrescos.repository;

import com.example.mercadofrescos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepo extends JpaRepository<User, Long> {
}
