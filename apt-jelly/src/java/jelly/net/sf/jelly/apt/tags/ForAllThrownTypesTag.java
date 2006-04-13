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

import com.sun.mirror.declaration.ExecutableDeclaration;
import net.sf.jelly.apt.strategies.ThrownTypeLoopStrategy;

/**
 * Iterates through each thrown type of an executable declaration.
 *
 * @author Ryan Heaton
 */
public class ForAllThrownTypesTag extends APTJellyTag<ThrownTypeLoopStrategy> {

  public ForAllThrownTypesTag() {
    super(new ThrownTypeLoopStrategy());
  }

  /**
   * The variable into which to store the current thrown type.
   *
   * @param var The variable into which to store the current thrown type.
   */
  public void setVar(String var) {
    strategy.setVar(var);
  }

  /**
   * Variable in which to store the value of the index.
   *
   * @param indexVar Variable in which to store the value of the index.
   */
  public void setIndexVar(String indexVar) {
    strategy.setIndexVar(indexVar);
  }

  /**
   * The specified declaration.
   *
   * @param declaration The specified declaration.
   */
  public void setDeclaration(ExecutableDeclaration declaration) {
    strategy.setDeclaration(declaration);
  }
}
