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
import net.sf.jelly.apt.TemplateBlock;
import net.sf.jelly.apt.decorations.declaration.DecoratedTypeDeclaration;
import net.sf.jelly.apt.decorations.declaration.PropertyDeclaration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Evaluates its body for all properties of a type declaration.
 *
 * @author Ryan Heaton
 */
public class PropertyDeclarationLoopStrategy<B extends TemplateBlock> extends MemberDeclarationLoopStrategy<PropertyDeclaration, B> {

  /**
   * The property declarations of the given type declaration.
   *
   * @return The property declarations of the given type declaration.
   */
  protected Collection<PropertyDeclaration> getMemberDeclarations(TypeDeclaration declaration) {
    if (declaration instanceof DecoratedTypeDeclaration) {
      return new ArrayList<PropertyDeclaration>(((DecoratedTypeDeclaration) declaration).getProperties());
    }
    else {
      return new ArrayList<PropertyDeclaration>();
    }
  }

}