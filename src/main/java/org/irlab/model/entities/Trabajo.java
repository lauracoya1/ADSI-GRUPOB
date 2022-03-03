package org.irlab.model.entities;

import java.util.Set;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "Trabajos")
public class Trabajo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name="vehiculo_id")
  private Vehiculo vehiculo;

  @ManyToOne
  @JoinColumn(name="responsable_id")
  private User responsable;

  @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        mappedBy="trabajo"
  )
  Set<Tarea> tareas;

  private int presupuesto;

  public Trabajo() {
      this.tareas = new HashSet<Tarea>();
  }

  public Long getId() {
      return id;
  }
  
  public int getPresupuesto() {
      return presupuesto;
  }

  public void setPresupuesto(int presupuesto) {
      this.presupuesto = presupuesto;
  }

  public Vehiculo getVehiculo() {
      return vehiculo;
  }

  public void setVehiculo(Vehiculo vehiculo) {
      this.vehiculo = vehiculo;
  }

  public User getResponsable() {
      return responsable;
  }

  public void setResponsable(User responsable) {
      this.responsable = responsable;
  }

  public Set<Tarea> getTareas() {
      return tareas;
  }

  public void addTarea(Tarea tarea) {
      this.tareas.add(tarea);
  }

  public void removeTarea(Tarea tarea) {
      this.tareas.remove(tarea);
  }

}
