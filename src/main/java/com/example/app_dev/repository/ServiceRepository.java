package com.example.app_dev.repository;


import com.example.app_dev.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, String> {

    @Query(value =  "select * from service " +
                    "inner join contract on contract.service_id = service.service_id " +
                    "where contract.contract_end_date < ?1 " +
                    "and contract.service_id not in (" +
                    "                select contract.service_id  " +
                    "                from contract " +
                    "                where contract.contract_end_date >= ?1 " +
                    "                group by contract.service_id ) " +
                    "group by contract.service_id",
            nativeQuery = true)
    List<Service> getListServiceAvailable(String date);
}
