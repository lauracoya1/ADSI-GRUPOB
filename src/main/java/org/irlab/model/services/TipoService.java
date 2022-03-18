package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Tipo;

public interface TipoService {

  List<Tipo> listAllTipos();
  void insertTipo(Tipo tipo);
  boolean exists(String nombre);
}
