package org.irlab.model.utils;

import org.irlab.model.entities.User;
import org.irlab.model.entities.Elevador;
import java.time.LocalDateTime;


public class ScheduleSlot {

    private final LocalDateTime fecha;
    private final User mecanico;
    private final Elevador elevador;

    public ScheduleSlot(LocalDateTime fecha, User mecanico, Elevador elevador) {
        this.fecha = fecha;
        this.mecanico = mecanico;
        this.elevador = elevador;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public User getMecanico() {
        return mecanico;
    }

    public Elevador getElevador() {
        return elevador;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(" Slot: ")
            .append(this.fecha.toString())
            .append(" | Mecanico: ")
            .append(this.mecanico.getName())
            .append(" | Elevador: ")
            .append(this.elevador.getCodigo());


        return sb.toString();
    }
}
