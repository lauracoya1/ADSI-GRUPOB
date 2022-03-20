package org.irlab.model.services;

import java.util.List;

import org.irlab.model.entities.Vehiculo;

public interface VehicleService {

  List<Vehiculo> listAllVehiculos();
  List<Vehiculo> listVehiculosFromUser(String dni);
  void insertVehiculo(Vehiculo vehiculo);
  boolean exists(String plate);
}
