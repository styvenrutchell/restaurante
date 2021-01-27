package com.transbank.restaurante.exception;

public class ProductNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProductNotFoundException(int id) {
		super(String.format("Producto con id %d no existe", id));
	}
	

}
