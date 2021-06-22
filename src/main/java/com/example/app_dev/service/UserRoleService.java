package com.example.app_dev.service;

import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import com.example.app_dev.model.UserRole;

public interface UserRoleService {

    void save(UserRole userRole);

    void createUserRole(User user, Employee employee);

    void updateUserRole(Employee employee);

    boolean isDirectorRole(User user);
}
