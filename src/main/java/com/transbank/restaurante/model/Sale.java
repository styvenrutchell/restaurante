package com.transbank.restaurante.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sale {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToMany(fetch = FetchType.EAGER ,cascade = {CascadeType.ALL})
	private List<ProductSaleDetail> productSaleDetail;
	
	private Date date;
	
	@OneToOne(cascade = {CascadeType.ALL})
	private Customer customer;
	
	
	public double getTotalSale() {
		
		return productSaleDetail.stream()
				.mapToDouble(v -> v.getProduct().getPrice() * v.getQuantity())
				.sum();
	}

}
