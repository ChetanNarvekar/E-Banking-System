package com.app.loan.exceptions;


/**
 * Custom exception class to handle scenarios where a requested resource is not found.
 * This class extends RuntimeException to provide a specific type of exception
 * for cases where a resource could not be located.
 */

public class ResourceNotFoundException extends RuntimeException{
	
	
	/**
     * Serial version UID for serialization compatibility.
     * Ensures that during de-serialization, the class version is compatible.
     */
	private static final long serialVersionUID = 1L;

	
	/**
     * Constructor for ResourceNotFoundException that takes an error message as a parameter.
     * 
     * @param message The error message associated with this exception, providing details about the missing resource.
     */
	public ResourceNotFoundException(String message)
	{
		super(message);
	}

}
