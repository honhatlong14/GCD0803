package com.example.app_dev.repository;


import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByUser(User user);
}
