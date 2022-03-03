package org.irlab.model.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Tareas")
public class Tarea {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="elevador_codigo")
  private Elevador elevador;

  @ManyToOne
  @JoinColumn(name="tipo_codigo")
  private Tipo tipo;

  @ManyToMany
  @JoinTable(
  name = "tareas_mecanicos", 
  joinColumns = @JoinColumn(name = "tarea_id"), 
  inverseJoinColumns = @JoinColumn(name = "mecanico_id"))
  Set<User> mecanicos;

  @ManyToOne
  @JoinColumn(name="trabajo_id", nullable=false)
  private Trabajo trabajo;

  private LocalDateTime dateTime;
  private int duracion;

  public Tarea () {
      this.mecanicos = new HashSet<User>();
  }

  public Tarea (Trabajo trabajo) {
      this.mecanicos = new HashSet<User>();
      this.trabajo = trabajo;
  }

  public Long getId() {
      return id;
  }

  public int getDuracion() {
      return duracion;
  }

  public void setDuracion(int duracion) {
      this.duracion = duracion;
  }

  public LocalDateTime getDateTime() {
      return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
      this.dateTime = dateTime;
  }

  public Elevador getElevador() {
      return elevador;
  }

  public void setElevador(Elevador elevador) {
      this.elevador = elevador;
  }

  public Tipo getTipo() {
      return tipo;
  }

  public void setTipo(Tipo tipo) {
      this.tipo = tipo;
  }

  public Set<User> getMecanicos() {
      return mecanicos;
  }

  public void addMecanico(User user) {
      this.mecanicos.add(user);
  }

  public void removeMecanico(User user) {
      this.mecanicos.remove(user);
  }

  public Trabajo getTrabajo() {
      return trabajo;
  }

  public void setTrabajo(Trabajo trabajo) {
      this.trabajo = trabajo;
  }

}
