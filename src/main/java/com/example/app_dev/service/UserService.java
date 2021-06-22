package com.example.app_dev.service;


import com.example.app_dev.model.User;
import org.springframework.validation.Errors;

import java.util.List;

public interface UserService {

    List<User> findAll();

    void save(User user);

    User createUserByUsername(String username);

    void changePassword(User user, String newPassword);

    void validateUsernameExist(String username, Errors errors);

    User findByUsername(String username);

}
