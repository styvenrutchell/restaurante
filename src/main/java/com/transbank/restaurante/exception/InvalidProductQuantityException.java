package com.transbank.restaurante.exception;

public class InvalidProductQuantityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidProductQuantityException() {
		super("La cantidad del producto debe ser igual o mayor a 1");
	}

}
