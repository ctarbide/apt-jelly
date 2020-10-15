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
package net.sf.jelly.apt.tags;

import net.sf.jelly.apt.strategies.MethodDeclarationLoopStrategy;

/**
 * Evaluates its body for all methods of a type declaration.
 *
 * @author Ryan Heaton
 */
public class ForAllMethodsTag extends ExecutableDeclarationLoopTag<MethodDeclarationLoopStrategy> {

  public ForAllMethodsTag() {
    super(new MethodDeclarationLoopStrategy());
  }

  /**
   * The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   *
   * @param returnTypeVar The variable to which to assign the return type (as a {@link com.sun.mirror.type.TypeMirror}).
   */
  public void setReturnTypeVar(String returnTypeVar) {
    strategy.setReturnTypeVar(returnTypeVar);
  }
}
