package org.irlab.model.services;

import java.time.LocalDateTime;
import java.util.List;

import org.irlab.model.entities.Tarea;
import org.irlab.model.utils.ScheduleSlot;

public interface TareaService {

  List<Tarea> listAllTareas();
  void insertTarea(Tarea tarea);
  List<ScheduleSlot> findSlots(LocalDateTime date, Tarea tarea);
}
