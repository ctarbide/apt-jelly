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

import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.sf.jelly.apt.strategies.TypeDeclarationLoopStrategy;

/**
 * Evaluates its body for each {@link TypeDeclaration type declaration}.
 * <p/>
 * Unless specified otherwise, {@link ClassDeclaration}s will be included and {@link InterfaceDeclaration}s will NOT be included.
 *
 * @author Ryan Heaton
 */
public class ForAllTypesTag extends AnnotationFilterableDeclarationLoopTag<TypeDeclarationLoopStrategy> {

  public ForAllTypesTag() {
    this(new TypeDeclarationLoopStrategy());
  }

  protected ForAllTypesTag(TypeDeclarationLoopStrategy strategy) {
    super(strategy);
  }

  /**
   * Whether to include interfaces in the collection of types over which to iterate.
   *
   * @param includeInterfaces Whether to include interfaces in the collection of types over which to iterate.
   */
  public void setIncludeInterfaces(boolean includeInterfaces) {
    strategy.setIncludeInterfaces(includeInterfaces);
  }

  /**
   * Whether to include classes in the collection of types over which to iterate.
   *
   * @param includeClasses Whether to include classes in the collection of types over which to iterate.
   */
  public void setIncludeClasses(boolean includeClasses) {
    strategy.setIncludeClasses(includeClasses);
  }

  /**
   * The package declaration containing the types.
   *
   * @param pckg The package declaration containing the types.
   */
  public void setPackage(PackageDeclaration pckg) {
    strategy.setPackage(pckg);
  }


}
