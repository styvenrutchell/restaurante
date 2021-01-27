package com.transbank.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transbank.restaurante.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
