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
  @ManyToOne
  @JoinColumn(name="user_role", nullable=false)
  private Role role;

  @ManyToMany(mappedBy = "mecanicos")
  Set<Tarea> tareas;


  private String greeting;
  private String password;

  public User() {
      this.tareas = new HashSet<Tarea>();
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

  @Override
  public String toString() {
      return "User(name=" + name + ", greeting=" + greeting + ")";
  }
}
