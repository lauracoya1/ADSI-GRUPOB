package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Cliente;

public interface ClientService {

  List<Cliente> listAllClients();
  void insertClient(Cliente cliente);
  boolean exists(String dni);
}
