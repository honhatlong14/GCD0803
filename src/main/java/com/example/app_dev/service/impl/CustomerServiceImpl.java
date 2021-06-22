package com.example.app_dev.service.impl;

import com.example.app_dev.model.Customer;
import com.example.app_dev.repository.CustomerRepository;
import com.example.app_dev.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository repository;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<Customer> findAllList() {
        return repository.findAll();
    }

    @Override
    public Customer findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Customer customer) {
        repository.save(customer);
    }

    @Override
    public void validateCustomerIdExist(Customer customer, Errors errors) {
        String id = customer.getCustomerId();
        Customer cus = findById(id);
        if (cus != null) {
            if (cus.getCustomerId().equals(customer.getCustomerId())) {
                errors.rejectValue("customerId", "cus.customerId.existed");
            }
        }
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Customer> searchCustomer(String search, Pageable pageable) {
        return repository.searchCustomer(search, pageable);
    }


}
