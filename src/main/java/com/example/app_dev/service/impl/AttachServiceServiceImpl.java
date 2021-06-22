package com.example.app_dev.service.impl;

import com.example.app_dev.model.AttachService;
import com.example.app_dev.repository.AttachServiceRepository;
import com.example.app_dev.service.AttachServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachServiceServiceImpl implements AttachServiceService {

    @Autowired
    private AttachServiceRepository repository;

    @Override
    public List<AttachService> findAll() {
        return repository.findAll();
    }
}
