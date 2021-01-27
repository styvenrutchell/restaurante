package com.transbank.restaurante.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Cantidad a vender de cada producto")
public class ProductRequestDto {
	@ApiModelProperty(notes = "id del producto a vender. Debe estar previamente creado en la base de datos ya que aún no existe una api para crear un producto")
	private int id;
	
	@ApiModelProperty(notes = "Debe incluir mínimo 1 producto", example = "2")
	private int quantity;

}
