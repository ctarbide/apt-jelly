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
import net.sf.jelly.apt.strategies.NestedTypeDeclarationLoopStrategy;

/**
 * Evaluates its body for all nested types in a type declaration.
 *
 * @author Ryan Heaton
 */
public class ForAllNestedTypesTag extends ForAllTypesTag {

  public ForAllNestedTypesTag() {
    super(new NestedTypeDeclarationLoopStrategy());
  }

  /**
   * The declaration for which to iterate over all nested types.
   *
   * @param declaration The declaration for which to iterate over all nested types.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    ((NestedTypeDeclarationLoopStrategy) strategy).setDeclaration(declaration);
  }

}
