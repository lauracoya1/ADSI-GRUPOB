package org.irlab.model.exceptions;

public class ElevadorNotFoundException extends Exception {
    public ElevadorNotFoundException(String message) {
        super(String.format("elevador not found: %s.", message));
    }

}
