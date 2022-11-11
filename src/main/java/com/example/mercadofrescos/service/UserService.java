package com.example.mercadofrescos.service;

import com.example.mercadofrescos.exception.NotFoundException;
import com.example.mercadofrescos.model.User;
import com.example.mercadofrescos.repository.IUserRepo;
import com.example.mercadofrescos.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepo repo;

    /**
     * Busca um User ou lança um erro caso não encontre
     * @author Gabriel
     * @param id do User
     */
    @Override
    public User findById(Long id) {
        Optional<User> user = repo.findById(id);
        return user.orElseThrow(() -> new NotFoundException("User not found"));
    }
}
