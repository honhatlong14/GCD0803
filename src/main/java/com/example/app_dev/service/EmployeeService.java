package com.example.app_dev.service;

import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    Page<Employee> findAll(Pageable pageable);

    List<Employee> findAllList();

    Employee findById(Integer id);

    Employee findByUser(User user);

    void saveCrypt(Employee employee);

    void save(Employee employee);

    void deleteById(Integer id);

    boolean checkPassword(String oldPw, Employee employee);
}
