package com.transbank.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.transbank.restaurante.dto.CustomerRequestDto;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
	
	@Id
	private long id;
	private String name;
	private String phoneNumber;
	private String emailAddress;
	
	public Customer(CustomerRequestDto customerDto) {
		this.id = customerDto.getId();
		this.name = customerDto.getName();
	}

	public Customer() {
		
	}
}
