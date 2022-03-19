package org.irlab.model.exceptions;

public class TareaNotFoundException extends Exception {
    public TareaNotFoundException(String message) {
        super(String.format("tarea not found: %s.", message));
    }

}
