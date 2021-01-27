package com.transbank.restaurante.dto;

import com.transbank.restaurante.model.ProductSaleDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Producto que se vendió")
public class ProductResponseDto {
	@ApiModelProperty(notes = "Nombre del producto")
	private String name;
	@ApiModelProperty(notes = "Cantidad que se vendió de este producto")
	private int quantity;
	
	public ProductResponseDto(ProductSaleDetail productSaleDetail) {
		this.name = productSaleDetail.getProduct().getName();
		this.quantity = productSaleDetail.getQuantity();
	}
	
	public ProductResponseDto() {
		
	}

}
