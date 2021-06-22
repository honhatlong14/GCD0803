package com.example.app_dev.repository;


import com.example.app_dev.model.Contract;
import com.example.app_dev.model.ContractDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractDetailRepository extends JpaRepository<ContractDetail, Integer> {

    List<ContractDetail> findAllByContract(Contract contract);
}
