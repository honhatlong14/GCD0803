package com.example.app_dev.service;


import com.example.app_dev.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import java.util.List;

public interface CustomerService {

    Page<Customer> findAll(Pageable pageable);

    List<Customer> findAllList();

    Customer findById(String id);

    void save(Customer customer);

    void validateCustomerIdExist(Customer customer, Errors errors);

    void deleteById(String id);

    Page<Customer> searchCustomer(String search, Pageable pageable);
}
