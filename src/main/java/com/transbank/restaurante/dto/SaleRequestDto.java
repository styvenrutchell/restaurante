package com.transbank.restaurante.dto;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Detalles de la venta a realizar")
public class SaleRequestDto {
	@ApiModelProperty(notes = "Lista de productos a vender")
	private List<ProductRequestDto> products;
	@ApiModelProperty(notes = "Fecha de la venta")
	private Date date;
	@ApiModelProperty(notes = "cliente del restaurante")
	private CustomerRequestDto customer;
	
}
