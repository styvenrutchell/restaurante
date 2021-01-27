package com.transbank.restaurante.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "Cliente del restaurante")
public class CustomerRequestDto {
	@ApiModelProperty(notes = "Identificación del cliente, si ya está previamente creado en la base de datos se le agregará la venta al cliente con este número de identificación, de lo contrario se creará un cliente nuevo")
	private long id;
	@ApiModelProperty(notes = "Nombre del cliente")
	private String name;

}
