package com.transbank.restaurante.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transbank.restaurante.dto.SaleRequestDto;
import com.transbank.restaurante.dto.SaleResponseDto;
import com.transbank.restaurante.service.SalesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
	private static final Logger LOG = LoggerFactory.getLogger(SalesController.class);
	private static final String JMS_DESTINATION = "sales";
	private static final String JMS_MESSAGE = "today";
	
	@Autowired
	private SalesService salesService;
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@ApiOperation(value = "Crea una venta de uno o varios productos en el restaurante", 
					notes = "Debe ingresar la identificación del cliente, si es un cliente nuevo también ingresa el nombre. "
			+ "Debe ingresar el id del producto y la cantidad, puede agregar uno o varios productos. "
			+ "Debe agregar la fecha de la venta.", response = SaleResponseDto.class)
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SaleResponseDto createSale(@RequestBody SaleRequestDto request) throws Exception {
		return salesService.createSale(request);
	}

	@ApiOperation(value = "Devuelve la cantidad de ventas del día", 
			response = SaleResponseDto.class, responseContainer = "List")
	@GetMapping
	public List<SaleResponseDto> getSalesByCurrentDate() {
		return salesService.getSalesByCurrentDate();
	}

	@ApiOperation(value = "Envía varios mensajes a una cola que llama el endpoint que devuelve las ventas del día actual", 
			notes = "Debe ingresar la cantidad de veces que desea que se envíe el mensaje, para simular un endpoint que tiene alto consumo.")
	@GetMapping("/{quantity}")
	public void createQueueGetSalesByCurrentDate(
			@ApiParam(value = " Cantidad de veces que se envíe el mensaje que consumirá el api GET /api/sales el cual trae las ventas del día", 
						required = true, example ="1")
				@PathVariable int quantity) {
		for (int i = 0; i < quantity; i++) {
			LOG.info("Sending message: " + JMS_MESSAGE + " to JMS destination: " + JMS_DESTINATION);

			jmsTemplate.convertAndSend(JMS_DESTINATION, JMS_MESSAGE);
		}

	}

}
