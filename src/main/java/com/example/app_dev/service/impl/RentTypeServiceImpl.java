package com.example.app_dev.service.impl;


import com.example.app_dev.model.RentType;
import com.example.app_dev.repository.RentTypeRepository;
import com.example.app_dev.service.RentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentTypeServiceImpl implements RentTypeService {

    @Autowired
    RentTypeRepository repository;

    @Override
    public List<RentType> findAll() {
        return repository.findAll();
    }
}
