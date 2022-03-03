package org.irlab.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehiculos")
public class Vehiculo {

  @Id
  @Column(unique = true, nullable = false)
  private String matricula;

  @ManyToOne
  @JoinColumn(name="client_dni", nullable=false)
  private Cliente cliente;

  public Vehiculo() {
  }

  public Vehiculo(String matricula, String greeting, Role role) {
      this.matricula = matricula;
  }

  public String getMatricula() {
      return matricula;
  }

  public Cliente getCliente() {
      return cliente;
  }

  public void setCliente(Cliente cliente) {
      this.cliente = cliente;
  }
}
