package com.transbank.restaurante.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.transbank.restaurante.model.Sale;

public interface SalesRepository extends JpaRepository<Sale, Integer>{
	List<Sale> findAllByDateBetween(Date dateStart, Date dateEnd);

}
