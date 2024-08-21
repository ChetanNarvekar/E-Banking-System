package com.app.loan.exceptions;


/**
 * Custom exception class for handling API-related errors.
 * This class extends RuntimeException to provide a specific exception type
 * for use in the application.
 */

public class ApiException extends RuntimeException {
	
	
	 /**
     * Serial version UID for serialization compatibility.
     * This ensures that during de-serialization, the class version is compatible.
     */
	private static final long serialVersionUID = 1L;

	
	/**
     * Constructor for ApiException that takes a message as a parameter.
     * 
     * @param message The error message associated with this exception.
     */
	public ApiException(String message)
	{
		super(message);
	}

}
