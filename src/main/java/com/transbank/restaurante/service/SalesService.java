package com.transbank.restaurante.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.transbank.restaurante.dto.CustomerRequestDto;
import com.transbank.restaurante.dto.ProductRequestDto;
import com.transbank.restaurante.dto.SaleRequestDto;
import com.transbank.restaurante.dto.SaleResponseDto;
import com.transbank.restaurante.exception.InvalidProductQuantityException;
import com.transbank.restaurante.exception.ProductNotFoundException;
import com.transbank.restaurante.model.Customer;
import com.transbank.restaurante.model.Product;
import com.transbank.restaurante.model.ProductSaleDetail;
import com.transbank.restaurante.model.Sale;
import com.transbank.restaurante.repository.CustomerRepository;
import com.transbank.restaurante.repository.ProductRepository;
import com.transbank.restaurante.repository.SalesRepository;

@Service
public class SalesService {
	private final ZoneId defaultZoneId = ZoneId.systemDefault();
	
	@Autowired
	private SalesRepository salesRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	public SaleResponseDto createSale(SaleRequestDto saleRequest) throws Exception {
		Sale saleToCreate = getSaleFromDto(saleRequest);
		Sale saleCreated = salesRepository.save(saleToCreate);
		
		return new SaleResponseDto(saleCreated);
	}
	
	public List<SaleResponseDto> getSalesByCurrentDate() {
		List<Sale> todaysSales = salesRepository.findAllByDateBetween(Date.from(LocalDate.now().atStartOfDay(defaultZoneId).toInstant()),new Date());
		if(!CollectionUtils.isEmpty(todaysSales)) {
			List<SaleResponseDto> todaysSalesResponse = todaysSales.stream()
					.map(SaleResponseDto::new)
					.collect(Collectors.toList());
			return todaysSalesResponse;
				    
		}
		
		return Collections.emptyList();
	}

	private Sale getSaleFromDto(SaleRequestDto saleRequest) throws Exception {
		Sale sale = new Sale();
		sale.setDate(saleRequest.getDate());
		sale.setProductSaleDetail(getProductsSaleDetailFromDto(saleRequest.getProducts()));
		Customer customer = getSaleCustomer(saleRequest.getCustomer());
		sale.setCustomer(customer);
		return sale;
	}

	private Customer getSaleCustomer(CustomerRequestDto customer) {
		Optional<Customer> customerAlreadyExist = customerRepository.findById(customer.getId());
		if (customerAlreadyExist.isPresent()) {
			return customerAlreadyExist.get();
		}
		return new Customer(customer);
	}

	private List<ProductSaleDetail> getProductsSaleDetailFromDto(List<ProductRequestDto> productsDto) throws Exception {
		List<ProductSaleDetail> productsSaleDetail = new ArrayList<>();
		for(ProductRequestDto dto : productsDto) {
			ProductSaleDetail productSaleDetail = new ProductSaleDetail();
			Optional<Product> product = productRepository.findById(dto.getId());
			product.orElseThrow(() -> new ProductNotFoundException(dto.getId()));		
			validateProductQuantity(dto.getQuantity());
			productSaleDetail.setProduct(product.get());
			productSaleDetail.setQuantity(dto.getQuantity());
			productsSaleDetail.add(productSaleDetail);
		}
		
		return productsSaleDetail;
	}

	private void validateProductQuantity(int quantity) throws InvalidProductQuantityException {
		if(quantity < 1) {
			throw new InvalidProductQuantityException();
		}
		
	}

}
