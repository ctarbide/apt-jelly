/**
 * Copyright 2006 Ryan Heaton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.jelly.apt.samplesource.services;

import net.sf.jelly.apt.samplesource.Family;
import net.sf.jelly.apt.samplesource.Person;

/**
 * Service for reading and editing families.
 *
 * @author Ryan Heaton
 * @author Some Other Author
 */
public class FamilyService {

  /**
   * Reads a family.
   * @param id The id of the family.
   * @return The family that was read.
   * @throws IllegalArgumentException If the id is invalid.
   * @exception NoClassDefFoundError If your classpath is messed up.
   */
  public Family readFamily(String id) throws IllegalArgumentException, NoClassDefFoundError {
    return null;
  }

  /**
   * Adds a child to a family.
   *
   * @param family The family to which to add a child.
   * @param child The child to add to the family.
   */
  public void addChild(Family family, Person child) {
  }

  /**
   * Removes a child from a family.
   *
   * @param family The family.
   * @param child The child to remove.
   * @return Whether the child was successfully removed.
   */
  public boolean removeChild(Family family, Person child) {
    return false;
  }

}
