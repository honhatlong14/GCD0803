package com.example.app_dev.service.impl;

import com.example.app_dev.model.Contract;
import com.example.app_dev.model.ContractDetail;
import com.example.app_dev.repository.ContractRepository;
import com.example.app_dev.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    ContractRepository repository;

    @Override
    public List<Contract> findAll() {
        return repository.findAll();
    }

    @Override
    public Contract findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void save(Contract contract) {
        contract.setContractTotalMoney(getTotalMoney(contract));
        repository.save(contract);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public double getTotalMoney(Contract contract) {
        int totalDay = 0;
        double totalMoney = 0;
        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getContractStartDate());
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getContractEndDate());

            totalDay = (int) TimeUnit.DAYS.convert((end.getTime() - start.getTime()), TimeUnit.MILLISECONDS);
            if (totalDay == 0) {
                totalDay = 1; // neu startDate = endDate thi hop dong ton tai it nhat 1 ngay
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        double cost = Double.parseDouble(contract.getService().getServiceCost());
        totalMoney += cost * totalDay;

        if (contract.getContractId() != null){
            Set<ContractDetail> detailSet = contract.getContractDetailSet();
            if (!detailSet.isEmpty()){
                for(ContractDetail detail : detailSet){
                    totalMoney += detail.getAttachService().getAttachServiceCost() * detail.getQuantity();
                }
            }
        }

        return totalMoney;
    }

    @Override
    public Page<Contract> getListUsingCustomer(String date, Pageable pageable) {
        return repository.getListUsingCustomer(date, pageable);
    }

    @Override
    public String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}

