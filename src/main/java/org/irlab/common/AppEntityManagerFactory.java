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

package org.irlab.common;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Holder for the EntityManagerFactory singleton
 */
public class AppEntityManagerFactory {

  private static final String DEFAULT_PERSISTENCE_UNIT = "example-jpa-app";

  private static String persistenceUnit = DEFAULT_PERSISTENCE_UNIT;

  private static EntityManagerFactory factory = null;

  private AppEntityManagerFactory() {
  }

  private static synchronized void createInstance() {
    if (factory == null) {
      factory = Persistence.createEntityManagerFactory(persistenceUnit);
    }
  }

  public static EntityManagerFactory getInstance() {
    if (factory == null) {
      createInstance();
    }
    return factory;
  }

  public static synchronized void close() {
    if (factory != null) {
      factory.close();
      factory = null;
    }
  }

  public static synchronized void configurePersistenceUnit(String unitName) {
    if (persistenceUnit.equals(unitName)) {
      return;
    }

    if (factory != null) {
      close();
    }

    persistenceUnit = unitName;
  }

  public static void resetPersistenceUnit() {
    if (!persistenceUnit.equals(DEFAULT_PERSISTENCE_UNIT)) {
      configurePersistenceUnit(DEFAULT_PERSISTENCE_UNIT);
    }
  }
}
