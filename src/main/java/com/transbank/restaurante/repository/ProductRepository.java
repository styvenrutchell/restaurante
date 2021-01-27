package com.transbank.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transbank.restaurante.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
