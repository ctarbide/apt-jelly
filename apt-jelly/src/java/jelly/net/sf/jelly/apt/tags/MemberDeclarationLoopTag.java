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

import com.sun.mirror.declaration.MemberDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.sf.jelly.apt.strategies.MemberDeclarationLoopStrategy;

/**
 * Loop tag through a collection of {@link MemberDeclaration}s of a type declaration.  Options are available to include the superclasses and superinterfaces for
 * the current type declaration.  By default, the members of superclasses and superinterfaces will not be looped through.
 *
 * @author Ryan Heaton
 */
public abstract class MemberDeclarationLoopTag<S extends MemberDeclarationLoopStrategy> extends AnnotationFilterableDeclarationLoopTag<S> {

  protected MemberDeclarationLoopTag(S strategy) {
    super(strategy);
  }

  /**
   * Whether to include superclasses.
   *
   * @param includeSuperclasses Whether to include superclasses.
   */
  public void setIncludeSuperclasses(boolean includeSuperclasses) {
    strategy.setIncludeSuperclasses(includeSuperclasses);
  }

  /**
   * Whether to include superinterfaces.
   *
   * @param includeSuperinterfaces Whether to include superinterfaces.
   */
  public void setIncludeSuperinterfaces(boolean includeSuperinterfaces) {
    strategy.setIncludeSuperinterfaces(includeSuperinterfaces);
  }

  /**
   * The specified declaration.
   *
   * @param declaration The specified declaration.
   */
  public void setDeclaration(TypeDeclaration declaration) {
    strategy.setDeclaration(declaration);
  }
}
