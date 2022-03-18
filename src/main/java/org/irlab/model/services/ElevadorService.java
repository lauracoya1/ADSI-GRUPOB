package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Elevador;

public interface ElevadorService {

  List<Elevador> listAllElevadores();
  void insertElevador(Elevador elevador);
  boolean exists(String codigo);
}
