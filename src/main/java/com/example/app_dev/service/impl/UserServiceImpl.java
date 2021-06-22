package com.example.app_dev.service.impl;


import com.example.app_dev.model.User;
import com.example.app_dev.repository.UserRepository;
import com.example.app_dev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public User createUserByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(username);
        user.setEnabled(true);

        save(user);
        return user;
    }

    @Override
    public void changePassword(User user, String newPassword) {
        if (!user.getPassword().equals(newPassword)) {
            user.setPassword(newPassword);
        }
        save(user);
    }

    @Override
    public void validateUsernameExist(String username, Errors errors) {
        User user = repository.findByUsername(username);
        if (user != null){
            if (user.getUsername().equals(username)) {
                errors.rejectValue("user", "emp.username.existed");
            }
        }
    }

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
