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

import com.sun.mirror.declaration.TypeDeclaration;
import org.apache.commons.jelly.JellyTagException;

import java.util.Collection;

/**
 * Evaluates its body for all nested types in the {@link ForAllTypesTag#getCurrentDeclaration() current type
 * declaration}.
 *
 * @author Ryan Heaton
 */
public class ForAllNestedTypesTag extends ForAllTypesTag {

  private TypeDeclaration declaration;

  /**
   * Limits the loop to all type declarations of the current type declaration.
   *
   * @return The collection of nested types.
   * @throws JellyTagException If not in a type declaration loop.
   */
  protected Collection<TypeDeclaration> getAllTypeDeclarations() throws JellyTagException {
    TypeDeclaration declaration = getDeclaration();
    if (declaration == null) {
      declaration = getCurrentTypeDeclaration();

      if (declaration == null) {
        throw new JellyTagException("The loop tag for nested type declarations must either be within a loop tag for type declarations the declaration must be specified.");
      }
    }

    return declaration.getNestedTypes();
  }

  /**
   * Get the {@link ForAllTypesTag#getCurrentDeclaration() current type declaration}
   *
   * @return The current type declaration.
   * @throws JellyTagException If this tag isn't in a type declaration loop.
   */
  protected TypeDeclaration getCurrentTypeDeclaration() throws JellyTagException {
    ForAllTypesTag tag = (ForAllTypesTag) findAncestorWithClass(ForAllTypesTag.class);
    return ((tag == null) ? null : tag.getCurrentDeclaration());
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

}
