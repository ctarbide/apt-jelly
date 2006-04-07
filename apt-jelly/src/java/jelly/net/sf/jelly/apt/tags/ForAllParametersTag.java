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
import com.sun.mirror.declaration.ParameterDeclaration;
import org.apache.commons.jelly.JellyTagException;

import java.util.Collection;

/**
 * Evaluates its body for all parameters of the current method or constructor declaration.
 *
 * @author Ryan Heaton
 */
public class ForAllParametersTag extends AnnotationFilterableDeclarationLoopTag<ParameterDeclaration> {

  private ExecutableDeclaration declaration;

  //Inherited.
  public Collection<ParameterDeclaration> getAllDeclarationsToConsiderForAnnotationFiltering() throws JellyTagException {
    ExecutableDeclaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentExecutableDeclaration();

      if (declaration == null) {
        throw new JellyTagException("The loop tag for parameters must either be within a loop tag for exectuable declarations or the declaration must be specified.");
      }
    }

    return declaration.getParameters();
  }

  /**
   * Get the {@link ExecutableDeclarationLoopTag#getCurrentDeclaration() current executable declaration}
   *
   * @return The current executable declaration.
   * @throws JellyTagException If this tag isn't in an executable declaration loop.
   */
  protected ExecutableDeclaration getCurrentExecutableDeclaration() throws JellyTagException {
    ExecutableDeclarationLoopTag tag = (ExecutableDeclarationLoopTag) findAncestorWithClass(ExecutableDeclarationLoopTag.class);
    ExecutableDeclaration currentDeclaration = tag.getCurrentDeclaration();

    if (currentDeclaration == null) {
      throw new JellyTagException("The loop tag for parameter declarations must be within a loop tag for exectuable declarations.");
    }

    return currentDeclaration;
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
}
