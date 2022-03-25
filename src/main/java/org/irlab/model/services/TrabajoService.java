package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Trabajo;

public interface TrabajoService {

  List<Trabajo> listAllTrabajos();
  List<Trabajo> listTrabajosFromVehicle(String matricula);
}
