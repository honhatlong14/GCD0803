package com.example.app_dev.service.impl;

import com.example.app_dev.repository.ServiceRepository;
import com.example.app_dev.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceRepository repository;

    @Override
    public List<com.example.app_dev.model.Service> findAll() {
        return repository.findAll();
    }

    @Override
    public List<com.example.app_dev.model.Service> getListServiceAvailable(String date) {
        return repository.getListServiceAvailable(date);
    }

    @Override
    public com.example.app_dev.model.Service findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(com.example.app_dev.model.Service service) {
        repository.save(service);
    }

    @Override
    public void validateServiceIdExist(com.example.app_dev.model.Service service, Errors errors) {
        String id = service.getServiceId();
        com.example.app_dev.model.Service ser = findById(id);
        if (ser != null){
            if (ser.getServiceId().equals(service.getServiceId())){
                errors.rejectValue("serviceId", "ser.serviceId.existed");
            }
        }
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public String createServiceName(com.example.app_dev.model.Service service) {
        String name = service.getServiceType().getServiceTypeName();
        name += service.getServiceId().substring(2, 7);
        return name;
    }
}
