package org.irlab.model.exceptions;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
	        super(String.format("User not found: %s.", message));
	    }
	
}
