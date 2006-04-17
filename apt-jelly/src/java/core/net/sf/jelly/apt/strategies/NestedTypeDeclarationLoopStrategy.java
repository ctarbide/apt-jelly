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

import com.sun.mirror.declaration.TypeDeclaration;

import java.util.Collection;

import net.sf.jelly.apt.TemplateBlock;

/**
 * Evaluates its body for all nested types in a type declaration.
 * 
 * @author Ryan Heaton
 */
public class NestedTypeDeclarationLoopStrategy<B extends TemplateBlock> extends TypeDeclarationLoopStrategy<B> {

  private TypeDeclaration declaration;

  /**
   * Limits the loop to all type declarations of the current type declaration.
   *
   * @return The collection of nested types.
   */
  protected Collection<TypeDeclaration> getAllTypeDeclarations() throws MissingParameterException {
    TypeDeclaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentTypeDeclaration();

      if (declaration == null) {
        throw new MissingParameterException("declaration");
      }
    }

    return declaration.getNestedTypes();
  }

  /**
   * The declaration for which to iterate over all nested types.
   *
   * @return The declaration for which to iterate over all nested types.
   */
  public TypeDeclaration getDeclaration() {
    return declaration;
  }

  /**
   * The declaration for which to iterate over all nested types.
   *
   * @param declaration The declaration for which to iterate over all nested types.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    this.declaration = declaration;
  }

  /**
   * Gets the current declaration (in a loop).
   *
   * @return the current declaration (in a loop).
   */
  protected TypeDeclaration getCurrentTypeDeclaration() {
    TypeDeclarationLoopStrategy loop = StrategyStack.get().findFirst(TypeDeclarationLoopStrategy.class);
    if (loop != null) {
      return loop.getCurrentDeclaration();
    }
    return null;
  }
}
