package org.irlab.model.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.irlab.model.entities.Tarea;

public interface TareaService {

  List<Tarea> listAllTareas();
  void insertTarea(Tarea tarea);
  List<LocalDateTime> findSlots(LocalDate date, Tarea tarea);
}
