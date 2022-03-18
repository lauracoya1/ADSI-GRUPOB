package org.irlab.model.exceptions;

public class ClientNotFoundException extends Exception {
    public ClientNotFoundException(String message) {
        super(String.format("client not found: %s.", message));
    }

}
