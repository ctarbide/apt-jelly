/*
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

package net.sf.jelly.apt.strategies;

import com.sun.mirror.declaration.ExecutableDeclaration;

/**
 * Loop strategy through a collection of {@link ExecutableDeclaration}s.
 *
 * @author Ryan Heaton
 */
public abstract class ExecutableDeclarationLoopStrategy<E extends ExecutableDeclaration> extends MemberDeclarationLoopStrategy<E> {

  /**
   * Get the current declaration as an executable declaration.
   *
   * @return The current declaration.
   */
  public E getCurrentDeclaration() {
    return super.getCurrentDeclaration();
  }

}
