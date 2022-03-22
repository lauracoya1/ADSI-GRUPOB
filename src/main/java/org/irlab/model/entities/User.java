/**
 * Copyright 2022 Information Retrieval Lab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.irlab.model.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/* Need to specify a different table name as user is a built-in function in
 * Apache Derby */
@Entity
@Table(name = "Users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique = true, nullable = false)
  private String name;
  private String apellido1;
  private String apellido2;
    @ManyToOne
  @JoinColumn(name="user_role", nullable=false)
  private Role role;

  @ManyToMany(mappedBy = "mecanicos")
  Set<Tarea> tareas;
  @Column(unique = true, nullable = false)
  private String dni;
  private String telefono;
  private LocalDateTime fechaAlta;



  private LocalDate fechaNacimiento;
  private String greeting;
  private String password;
  private Long cuentaBancaria;

  public User() {
      this.tareas = new HashSet<Tarea>();
  }

    public User(String dni) {
        this.dni = dni;
        this.tareas = new HashSet<Tarea>();
        this.fechaAlta = LocalDateTime.now();

    }

    public User(String dni, String name, String greeting, Role role) {
        this.tareas = new HashSet<Tarea>();
        this.dni = dni;
        this.name = name;
        this.greeting = greeting;
        this.role = role;
    }

  public User(String name, String greeting, Role role) {
      this.tareas = new HashSet<Tarea>();
      this.name = name;
      this.greeting = greeting;
      this.role = role;
  }

  public Long getId() {
      return id;
  }

  public String getName() {
      return name;
  }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public String getGreeting() {
      return greeting;
  }

  public void setGreeting(String greeting) {
      this.greeting = greeting;
  }

  public Role getRole() {
      return role;
  }

  public void setRole(Role role) {
      this.role = role;
  }

  public String getPassword() {
      return password;
  }

  public void setPassword(String password) {
      this.password = password;
  }

  public Set<Tarea> getTareas() {
      return tareas;
  }

  public String getTelefono() {
      return telefono;
  }

  public void setTelefono(String telefono) {
      this.telefono = telefono;
  }

  public LocalDate getFechaNacimiento() {
      return fechaNacimiento;
  }

  public void setFechaNacimiento(LocalDate fechaNacimiento) {
      this.fechaNacimiento = fechaNacimiento;
  }

  public LocalDateTime getFechaAlta() {
      return fechaAlta;
  }

    public Long getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(Long cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Usuario: ")
            .append(this.getName())
            .append("(" + this.role.getRoleName() + ")");
        return sb.toString();
    }
}
