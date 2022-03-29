package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Trabajo;

import javax.annotation.Nonnull;

public interface TrabajoService {

  List<Trabajo> listAllTrabajos();
  List<Trabajo> listTrabajosFromVehicle(String matricula);
  void insertTrabajo(Trabajo trabajo);
}
