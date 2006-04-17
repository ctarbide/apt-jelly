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
import com.sun.mirror.declaration.ParameterDeclaration;
import net.sf.jelly.apt.TemplateBlock;

import java.util.Collection;

/**
 * Evaluates its body for all parameters of the current (or given) method or constructor declaration.
 *
 * @author Ryan Heaton
 */
public class ParameterDeclarationLoopStrategy<B extends TemplateBlock> extends AnnotationFilterableDeclarationLoopStrategy<ParameterDeclaration, B> {

  private ExecutableDeclaration declaration;

  //Inherited.
  public Collection<ParameterDeclaration> getAllDeclarationsToConsiderForAnnotationFiltering() throws MissingParameterException {
    ExecutableDeclaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentExecutableDeclaration();
      
      if (declaration == null) {
        throw new MissingParameterException("declaration");
      }
    }

    return declaration.getParameters();
  }

  /**
   * The specified declaration.
   *
   * @return The specified declaration.
   */
  public ExecutableDeclaration getDeclaration() {
    return declaration;
  }

  /**
   * The specified declaration.
   *
   * @param declaration The specified declaration.
   */
  public void setDeclaration(ExecutableDeclaration declaration) {
    this.declaration = declaration;
  }

  /**
   * Gets the current declaration (in a loop).
   *
   * @return the current declaration (in a loop).
   */
  protected ExecutableDeclaration getCurrentExecutableDeclaration() {
    ExecutableDeclarationLoopStrategy loop = StrategyStack.get().findFirst(ExecutableDeclarationLoopStrategy.class);
    if (loop != null) {
      return loop.getCurrentDeclaration();
    }
    return null;
  }
}
