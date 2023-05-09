package com.stock.service.impl;

import com.stock.entity.User;
import com.stock.model.RegisterModel;
import com.stock.repository.UserRepository;
import com.stock.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void signUp(RegisterModel registerModal) {
        User user = new User();
        user.setName(registerModal.getName());
        user.setEmail(registerModal.getEmail());
        user.setPassword(passwordEncoder.encode(registerModal.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public User logIn(String email) {
        return userRepository.getUserByUsername(email);
    }
}
