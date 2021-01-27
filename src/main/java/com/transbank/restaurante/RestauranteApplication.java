package com.transbank.restaurante;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class RestauranteApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RestauranteApplication.class, args);

	}
	
	@Bean
	public Docket swaggerConfiguration() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/**"))
				.apis(RequestHandlerSelectors.basePackage("com.transbank.restaurante.controller"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		
		return new ApiInfo(
				"API Restaurante Transbank",
				"API para gestionar ventas en restaurante, prueba técnica para Transbank",
				"1.0",
				"Uso libre",
				new Contact("Styven Palacio", "https://www.linkedin.com/in/styvenrutchell/", "spalacio@nisum.com"),
				"API License",
				"https://www.linkedin.com/in/styvenrutchell/",
				Collections.emptyList());
	}
	
	

}
