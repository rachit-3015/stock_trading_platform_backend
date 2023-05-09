package com.stock.service;


import com.stock.entity.User;
import com.stock.model.RegisterModel;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void signUp(RegisterModel registerModel);
    User logIn(String email);
}
