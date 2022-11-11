package com.example.mercadofrescos.service.interfaces;

import com.example.mercadofrescos.model.User;

public interface IUserService {
    User findById(Long id);
}
