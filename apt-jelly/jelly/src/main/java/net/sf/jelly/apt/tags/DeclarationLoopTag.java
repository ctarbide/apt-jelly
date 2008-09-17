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

import net.sf.jelly.apt.strategies.DeclarationLoopStrategy;
import net.sf.jelly.apt.APTJellyTag;

/**
 * A tag that is the loop over a set of declarations.
 *
 * @author Ryan Heaton
 */
public abstract class DeclarationLoopTag<S extends DeclarationLoopStrategy> extends APTJellyTag<S> {

  protected DeclarationLoopTag(S strategy) {
    super(strategy);
  }

  /**
   * The context variable in which to store the declaration.
   *
   * @param var The context variable in which to store the declaration.
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

}
