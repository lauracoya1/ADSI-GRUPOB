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

import org.irlab.model.entities.Tarea;
import org.irlab.model.entities.User;
import org.irlab.model.exceptions.NoTareasException;
import org.irlab.model.exceptions.UserNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The user service facade
 */
public interface UserService {

    /**
     * Get a string greeting the user
     *
     * @param name the name of the user
     * @return the greeting message
     */
    String greet(String name);

    /**
     * Change the user greeting message
     *
     * @param name     the name of the user
     * @param greeting the greeting message
     */
    void setUserGreeting(String name, String greeting) throws UserNotFoundException;

    /**
     * Check user credentials 
     *
     * @param name     the name of the user
     * @param password the password of the user
     *
     * @return boolean indicating if credentials are correct
     */
    boolean checkCredentials(String name, String password);


    void insertUser(User user);
    boolean exists(String dni);

    List<User> listAllUsers();

    List<Tarea> showHorario(String user, LocalDate fecha) throws NoTareasException;

    boolean isBusy(User u, LocalDateTime fechaHora);
    boolean isAdmin(User user);

    User getByUserName(String user) throws UserNotFoundException;
}
