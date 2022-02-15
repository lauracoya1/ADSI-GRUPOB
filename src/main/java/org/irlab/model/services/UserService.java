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

import org.irlab.model.entities.User;
import org.irlab.model.exceptions.UserNotFoundException;

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

  List<User> listAllUsers();
}
