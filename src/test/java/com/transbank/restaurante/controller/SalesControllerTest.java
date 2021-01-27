package com.transbank.restaurante.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transbank.restaurante.dto.CustomerRequestDto;
import com.transbank.restaurante.dto.ProductRequestDto;
import com.transbank.restaurante.dto.SaleRequestDto;
import com.transbank.restaurante.dto.SaleResponseDto;

@SpringBootTest
@WebAppConfiguration
public class SalesControllerTest {
	protected MockMvc mvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	private void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}
	
	@Test
	public void createSaleTest() throws Exception {
		String uri = "/api/sales";
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		saleRequest.setProducts(getTestProducts());
		
		String inputJson = mapToJson(saleRequest);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			   
			   int status = mvcResult.getResponse().getStatus();
			   assertEquals(201, status);
			   String content = mvcResult.getResponse().getContentAsString();
			   SaleResponseDto saleResponse = mapFromJson(content, SaleResponseDto.class);
			   assertEquals(85000, saleResponse.getTotalPrice());
			   assertEquals(3, saleResponse.getProducts().size());
		
		
	}
	
	@Test
	public void createSaleWithInvalidProductTest() throws Exception {
		String uri = "/api/sales";
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		ProductRequestDto product = new ProductRequestDto();
		product.setId(9);
		product.setQuantity(5);
		saleRequest.setProducts(Arrays.asList(product));
		
		String inputJson = mapToJson(saleRequest);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			   
			   int status = mvcResult.getResponse().getStatus();
			   assertEquals(404, status);
			   String content = mvcResult.getResponse().getContentAsString();
			   assertTrue(content.contains("Producto no encontrado"));
		
	}
	
	@Test
	public void createSaleWithInvalidProductQuantityTest() throws Exception {
		String uri = "/api/sales";
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		ProductRequestDto product = new ProductRequestDto();
		product.setId(1);
		product.setQuantity(0);
		saleRequest.setProducts(Arrays.asList(product));
		
		String inputJson = mapToJson(saleRequest);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();
			   
			   int status = mvcResult.getResponse().getStatus();
			   assertEquals(400, status);
			   String content = mvcResult.getResponse().getContentAsString();
			   assertTrue(content.contains("Cantidad invalida ingresada"));
		
	}
	
	@Test
	public void getSalesOfCurrentDay() throws Exception {
		String uri = "/api/sales";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
			      .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
			   
			   int status = mvcResult.getResponse().getStatus();
			   assertEquals(200, status);
			   String content = mvcResult.getResponse().getContentAsString();
			   List<SaleResponseDto> salesResponse = mapFromJson(content, List.class);
			   assertEquals(1,salesResponse.size());
		
		
	}
	
	
	private CustomerRequestDto getTestCustomer() {
		CustomerRequestDto customer = new CustomerRequestDto();
		customer.setId(1);
		customer.setName("testCustomer");
		return customer;
	}
	
	private List<ProductRequestDto> getTestProducts() {
		
		ProductRequestDto product1 = new ProductRequestDto();
		product1.setId(1);
		product1.setQuantity(2);
		
		ProductRequestDto product2 = new ProductRequestDto();
		product2.setId(2);
		product2.setQuantity(3);
		
		ProductRequestDto product3 = new ProductRequestDto();
		product3.setId(3);
		product3.setQuantity(1);
		
		return Arrays.asList(product1,product2,product3);
	}


}
