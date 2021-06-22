package com.example.app_dev.service.impl;

import com.example.app_dev.model.Division;
import com.example.app_dev.repository.DivisionRepository;
import com.example.app_dev.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivisionServiceImpl implements DivisionService {

    @Autowired
    DivisionRepository repository;

    @Override
    public List<Division> findAll() {
        return repository.findAll();
    }
}
