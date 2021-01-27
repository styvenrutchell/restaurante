package com.transbank.restaurante.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.transbank.restaurante.model.Sale;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Detalles de la venta que se realizó")
public class SaleResponseDto {
	@ApiModelProperty(notes = "ID de la venta")
	private int id;
	@ApiModelProperty(notes = "Lista de productos")
	private List<ProductResponseDto> products;
	@ApiModelProperty(notes = "Nombre del cliente")
	private String customer;
	@ApiModelProperty(notes = "Fecha de la venta")
	private Date date;
	@ApiModelProperty(notes = "Precio total de la venta")
	private double totalPrice;
	
	public SaleResponseDto (Sale sale) {
		this.id = sale.getId();

		this.products = sale.getProductSaleDetail().stream()
				.map(ProductResponseDto :: new)
				.collect(Collectors.toList());
		this.date = sale.getDate();
		this.totalPrice = sale.getTotalSale();
		if(sale.getCustomer() != null) {
			this.customer = sale.getCustomer().getName();
		}
				
	}
	
	public SaleResponseDto() {
		
	}

}
