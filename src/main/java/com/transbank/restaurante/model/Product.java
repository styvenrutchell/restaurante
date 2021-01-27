package com.transbank.restaurante.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

	@Id
	private int id;
	private String name;
	private double price;
	private String description;
	

}
