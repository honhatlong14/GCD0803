package com.example.app_dev.service.impl;


import com.example.app_dev.model.ServiceType;
import com.example.app_dev.repository.ServiceTypeRepository;
import com.example.app_dev.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService {

    @Autowired
    ServiceTypeRepository repository;

    @Override
    public List<ServiceType> findAll() {
        return repository.findAll();
    }
}
