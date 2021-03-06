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

import com.sun.mirror.declaration.ConstructorDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;

import java.util.Collection;
import java.util.ArrayList;

import net.sf.jelly.apt.TemplateBlock;

/**
 * Evaluates its body for all constructors of the specified type declaration.  If the specified type declaration is an
 * {@link com.sun.mirror.declaration.InterfaceDeclaration}, there will be no constructors.
 *
 * @author Ryan Heaton
 */
public class ConstructorDeclarationLoopStrategy<B extends TemplateBlock> extends ExecutableDeclarationLoopStrategy<ConstructorDeclaration, B> {

  /**
   * All the constructors of the given declaration declaration, or an empty list if the current declaration
   * is an interface declaration.
   *
   * @return All the constructors of the current declaration declaration.
   */
  protected Collection<ConstructorDeclaration> getMemberDeclarations(TypeDeclaration declaration) {
    if (declaration instanceof ClassDeclaration) {
      return ((ClassDeclaration) declaration).getConstructors();
    }
    else {
      //if it's an interface delcaration, there are no constructors.
      return new ArrayList<ConstructorDeclaration>();
    }
  }

}
