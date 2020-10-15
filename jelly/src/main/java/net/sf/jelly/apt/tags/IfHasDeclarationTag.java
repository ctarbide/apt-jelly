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

import com.sun.mirror.type.TypeMirror;
import net.sf.jelly.apt.strategies.IfHasDeclarationStrategy;
import net.sf.jelly.apt.APTJellyTag;

/**
 * Evalute the body of the tag if the specified type has a declaration.  If the specified type is an
 * array type, the body will not be evaluated.
 *
 * @author Ryan Heaton
 */
public class IfHasDeclarationTag extends APTJellyTag<IfHasDeclarationStrategy> {

  public IfHasDeclarationTag() {
    super(new IfHasDeclarationStrategy());
  }

  /**
   * The type on which to check for a declaration.
   *
   * @param type The type on which to check for a declaration.
   */
  public void setType(TypeMirror type) {
    strategy.setType(type);
  }

  /**
   * The variable to which to assign the declaration variable, if it exists.
   *
   * @param declarationVar The variable to which to assign the declaration variable, if it exists.
   */
  public void setDeclarationVar(String declarationVar) {
    strategy.setDeclarationVar(declarationVar);
  }

}
