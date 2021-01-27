package com.transbank.restaurante.consumer;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.transbank.restaurante.dto.SaleResponseDto;

@Component
public class SalesConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(SalesConsumer.class);
	private static final String TODAY = "today";
	private static final String SALES_URL = "http://localhost:8080/api/sales" ;
			
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HttpHeaders httpHeaders;
	
	@JmsListener(destination = "sales" , containerFactory = "restaurantContainerFactory")
	public void receiveGetSalesForTodayMessage(String message) {
		if(message.equals(TODAY)) {
			HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
			ResponseEntity<List<SaleResponseDto>> responseEntity = restTemplate.exchange(
					SALES_URL,
					HttpMethod.GET,
					httpEntity,
					new ParameterizedTypeReference<List<SaleResponseDto>>(){
						
					});
			List<SaleResponseDto> salesToday = responseEntity.getBody();
			LOG.info("Sales of "+ LocalDate.now() + " "+salesToday.toString());

			
		} else {
			LOG.warn("Method to retrieve sales of: "+message + " has not been implemented");
		}
		
	}
	
	

}
