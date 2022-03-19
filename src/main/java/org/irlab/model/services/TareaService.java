package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Tarea;

public interface TareaService {

  List<Tarea> listAllTareas();
  void insertTarea(Tarea tarea);
}
