package com.example.app_dev.service.impl;

import com.example.app_dev.model.Position;
import com.example.app_dev.repository.PositionRepository;
import com.example.app_dev.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository repository;

    @Override
    public List<Position> findAll() {
        return repository.findAll();
    }
}
