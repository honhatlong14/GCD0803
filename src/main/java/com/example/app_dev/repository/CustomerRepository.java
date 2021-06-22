package com.example.app_dev.repository;


import com.example.app_dev.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query(value =  "select * from customer " +
                    "where concat(customer_id, customer_name, customer_id_card, customer_address) like %?1%",
            nativeQuery = true)
    Page<Customer> searchCustomer(String search, Pageable pageable);

}
