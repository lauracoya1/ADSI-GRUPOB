package org.irlab.model.exceptions;

import org.irlab.model.entities.User;

public class NoTareasException extends Exception{
    private String user;

    public NoTareasException (String user){
        super("El usuario "+ user +" no tiene tareas para este día");
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
