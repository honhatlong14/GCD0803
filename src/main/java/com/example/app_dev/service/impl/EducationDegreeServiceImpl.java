package com.example.app_dev.service.impl;

import com.example.app_dev.model.EducationDegree;
import com.example.app_dev.repository.EducationDegreeRepository;
import com.example.app_dev.service.EducationDegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationDegreeServiceImpl implements EducationDegreeService {

    @Autowired
    EducationDegreeRepository repository;

    @Override
    public List<EducationDegree> findAll() {
        return repository.findAll();
    }
}
