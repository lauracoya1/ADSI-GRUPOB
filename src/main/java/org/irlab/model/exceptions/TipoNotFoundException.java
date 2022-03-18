package org.irlab.model.exceptions;

public class TipoNotFoundException extends Exception {
    public TipoNotFoundException(String message) {
        super(String.format("Tipo not found: %s.", message));
    }
}
