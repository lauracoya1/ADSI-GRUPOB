package org.irlab.model.exceptions;

public class VehiculoNotFoundException extends Exception {
    public VehiculoNotFoundException(String message) {
        super(String.format("Vehicle not found: %s.", message));
    }

}
