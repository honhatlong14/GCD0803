package com.example.app_dev.service.impl;

import com.example.app_dev.model.CustomerType;
import com.example.app_dev.repository.CustomerTypeRepository;
import com.example.app_dev.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTypeServiceImpl implements CustomerTypeService {

    @Autowired
    CustomerTypeRepository repository;

    @Override
    public List<CustomerType> findAll() {
        return repository.findAll();
    }
}
