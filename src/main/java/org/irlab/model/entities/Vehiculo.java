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

  private String tipo;

  @ManyToOne
  @JoinColumn(name="client_dni", nullable=false)
  private Cliente cliente;

  public Vehiculo() {
  }

  public Vehiculo(String matricula) {
      this.matricula = matricula;
  }

  public Vehiculo(String matricula, String tipo, Cliente cliente) {
      this.matricula = matricula;
      this.cliente = cliente;
      this.tipo = tipo;
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

  public void setTipo(String tipo) {
      this.tipo = tipo;
  }

  public String getTipo() {
      return tipo;
  }

  @Override
  public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Vehiculo con matricula: ")
          .append(this.matricula)
          .append(" del cliente ")
          .append(cliente.getDni());

      return sb.toString();
  }
}
