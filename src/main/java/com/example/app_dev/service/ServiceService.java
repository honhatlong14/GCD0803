package com.example.app_dev.service;

import com.example.app_dev.model.Service;
import org.springframework.validation.Errors;

import java.util.List;

public interface ServiceService {

    List<Service> findAll();

    List<Service> getListServiceAvailable(String date);

    Service findById(String id);

    void save(Service service);

    void validateServiceIdExist(Service service, Errors errors);

    void deleteById(String id);

    String createServiceName(Service service);
}
