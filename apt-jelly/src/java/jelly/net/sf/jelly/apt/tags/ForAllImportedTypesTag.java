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
import net.sf.jelly.apt.strategies.ImportedTypeDeclarationLoopStrategy;

/**
 * Evaluates its body for all imported types of a specified type declaration.  By default,
 * both classes and interfaces are included.  This list will include the bounds of any formal
 * type parameters.  This list will NOT include annotations used by the specified type
 * declaration.
 *
 * @author Ryan Heaton
 */
public class ForAllImportedTypesTag extends ForAllTypesTag {

  public ForAllImportedTypesTag() {
    super(new ImportedTypeDeclarationLoopStrategy());
    setIncludeClasses(true);
    setIncludeInterfaces(true);
  }

  /**
   * The type declaration for which to get all imported types.
   *
   * @param declaration The type declaration for which to get all imported types.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    ((ImportedTypeDeclarationLoopStrategy) strategy).setDeclaration(declaration);
  }

}
