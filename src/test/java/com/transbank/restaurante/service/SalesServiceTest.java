package com.transbank.restaurante.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.transbank.restaurante.dto.CustomerRequestDto;
import com.transbank.restaurante.dto.ProductRequestDto;
import com.transbank.restaurante.dto.SaleRequestDto;
import com.transbank.restaurante.dto.SaleResponseDto;
import com.transbank.restaurante.exception.InvalidProductQuantityException;
import com.transbank.restaurante.exception.ProductNotFoundException;

@SpringBootTest
public class SalesServiceTest {
	
	@Autowired
	private SalesService salesService;
	
	
	@Test
	public void createSaleTest() throws Exception {
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		saleRequest.setProducts(getTestProducts());
		
		SaleResponseDto saleResponse = salesService.createSale(saleRequest);
		
		assertThat(saleResponse.getTotalPrice())
			.isEqualTo(85000);
	}
	
	@Test
	public void createSaleWithInvalidProductTest() throws Exception {
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		ProductRequestDto product = new ProductRequestDto();
		product.setId(9);
		product.setQuantity(5);
		saleRequest.setProducts(Arrays.asList(product));
		
		Exception exception = assertThrows(ProductNotFoundException.class, () -> salesService.createSale(saleRequest));
		String expectedMessage = "Producto con id 9 no existe";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void createSaleWithInvalidProductQuantityTest() throws Exception {
		SaleRequestDto saleRequest = new SaleRequestDto();
		saleRequest.setCustomer(getTestCustomer());
		saleRequest.setDate(new Date());
		ProductRequestDto product = new ProductRequestDto();
		product.setId(2);
		product.setQuantity(0);
		saleRequest.setProducts(Arrays.asList(product));
		
		Exception exception = assertThrows(InvalidProductQuantityException.class, () -> salesService.createSale(saleRequest));
		String expectedMessage = "La cantidad del producto debe ser igual o mayor a 1";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	public void getSalesByCurrentDateTest() throws Exception {
		SaleRequestDto saleRequest = new SaleRequestDto();
		CustomerRequestDto customer = new CustomerRequestDto();
		customer.setId(2);
		customer.setName("customerTest2");
		saleRequest.setCustomer(customer);
		saleRequest.setDate(new Date());
		saleRequest.setProducts(getTestProducts());
		
		salesService.createSale(saleRequest);
		
		List<SaleResponseDto> sales = salesService.getSalesByCurrentDate();
		assertThat(sales.size())
			.isEqualTo(2);
		assertThat(sales.get(0).getCustomer())
		    .isEqualTo("testCustomer");
		assertThat(sales.get(0).getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
			.isAfter(LocalDate.now().plusDays(-1))
			.isBefore(LocalDate.now().plusDays(1));
			
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
