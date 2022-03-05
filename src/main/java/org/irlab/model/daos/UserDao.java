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

package org.irlab.model.daos;

import java.util.List;
import java.util.Optional;

import org.irlab.model.entities.Tarea;
import org.irlab.model.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.TypedQuery;

public class UserDao {

  private UserDao() {
  }

  /**
   * Update the user details
   *
   * @param em   the entity manager
   * @param user the user
   */
  public static void update(EntityManager em, User user) {
    em.persist(user);
  }

  /**
   * Find user by name
   *
   * @param em   the entity manager
   * @param name the user name
   * @return the user or an empty optional if it does not exists
   * @throws NonUniqueResultException if there is more than one user with the name
   */
  public static Optional<User> findByName(EntityManager em, String name) {
    TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class);
    q.setParameter("name", name);
    List<User> queryResult = q.getResultList();
    if (queryResult.size() > 1) {
      throw new NonUniqueResultException();
    } else if (queryResult.size() == 1) {
      return Optional.of(queryResult.get(0));
    } else {
      return Optional.empty();
    }
  }

  public static List<User> getAllUsers(EntityManager em) {
    TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
    return q.getResultList();

  }

  public static List<Tarea> getAllTareas(EntityManager em, String name){
    TypedQuery<Tarea> q = em.createQuery("SELECT t FROM Tarea t", Tarea.class);
    return q.getResultList();

  }

}