package org.irlab.model.exceptions;

public class TrabajoNotFoundException extends Exception {
    public TrabajoNotFoundException(String message) {
        super(String.format("trabajo not found: %s.", message));
    }

}
