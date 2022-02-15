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

package org.irlab.model.services;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import org.irlab.common.AppEntityManagerFactory;
import org.irlab.model.daos.UserDao;
import org.irlab.model.entities.Role;
import org.irlab.model.entities.User;
import org.irlab.model.exceptions.UserNotFoundException;

import jakarta.persistence.EntityManager;

/**
 * Implementation of the user service facade
 */
public class UserServiceImpl implements UserService {

  private static final String DEFAULT_GREETING = "Sorry";
  private static final String DEFAULT_USER = "User not found";
  private static final String DEFAULT_ROLE = "Role NA"; 

  private static final String MESSAGE_FORMAT = "%s, %s!";

  @Override
  @Nonnull
  public String greet(@Nonnull String name) {
    Preconditions.checkNotNull(name, "name cannot be null");

    EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();
    try {
      Optional<User> maybeUser = UserDao.findByName(em, name);
      User u = maybeUser.orElseGet(() -> new User(DEFAULT_GREETING, DEFAULT_USER, new Role(DEFAULT_ROLE)));
      
	  return (String.format(Locale.ENGLISH, MESSAGE_FORMAT, u.getGreeting(), u.getName()));
    } finally {
      em.close();
    }
  }

  @Override
  public void setUserGreeting(@Nonnull String name, @Nonnull String greeting) throws UserNotFoundException {
    Preconditions.checkNotNull(name, "name cannot be null");
    Preconditions.checkNotNull(greeting, "greeting cannot be null");

    EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();
    try {
      User user = UserDao
          .findByName(em, name)
          .map(u -> {
            u.setGreeting(greeting);
            return u;
          }).orElseThrow(() -> new UserNotFoundException(String.format("with name %s", name)));

      em.getTransaction().begin();
      UserDao.update(em, user);
      em.getTransaction().commit();
    } catch (UserNotFoundException e) {
    	throw e;
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }

  }

  @Override
  public List<User> listAllUsers() {
    EntityManager em = AppEntityManagerFactory.getInstance().createEntityManager();
    try {
      List<User> userList = UserDao.getAllUsers(em);
      em.getTransaction().begin();
      em.getTransaction().commit();
      return userList;
    } catch (Exception e) {
      em.getTransaction().rollback();
      throw e;
    } finally {
      em.close();
    }

  }

}
