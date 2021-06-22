package com.example.app_dev.service;


import com.example.app_dev.model.Contract;
import com.example.app_dev.model.ContractDetail;

import java.util.List;

public interface ContractDetailService {

    List<ContractDetail> findAll();

    List<ContractDetail> findAllByContract(Contract contract);

    void save(ContractDetail contractDetail);
}
