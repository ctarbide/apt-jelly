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
import com.sun.mirror.declaration.TypeDeclaration;
import org.apache.commons.jelly.JellyTagException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Evaluates its body for each {@link TypeDeclaration type declaration}.
 * <p/>
 * Unless specified otherwise, {@link ClassDeclaration}s will be included and {@link InterfaceDeclaration}s will NOT be included.
 *
 * @author Ryan Heaton
 */
public class ForAllTypesTag extends AnnotationFilterableDeclarationLoopTag<TypeDeclaration> {

  private boolean includeClasses = true;
  private boolean includeInterfaces = false;

  /**
   * The base collection of types over which to iterate.
   *
   * @return The base collection of types over which to iterate.
   */
  public Collection<TypeDeclaration> getAllDeclarationsToConsiderForAnnotationFiltering() throws JellyTagException {
    Collection<TypeDeclaration> allTypeDeclarations = getAllTypeDeclarations();
    if (includeClasses && includeInterfaces) {
      return allTypeDeclarations;
    }
    else {
      ArrayList<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>();
      for (TypeDeclaration declaration : allTypeDeclarations) {
        if (((includeClasses) && (declaration instanceof ClassDeclaration)) ||
          ((includeInterfaces) && (declaration instanceof InterfaceDeclaration))) {
          typeDeclarations.add(declaration);
        }
      }
      return typeDeclarations;
    }
  }

  /**
   * Get all type declarations from the {@link #getAnnotationProcessorEnvironment() annotation
   * processor environment}.
   *
   * @return All type declarations considered for this loop tag.
   */
  protected Collection<TypeDeclaration> getAllTypeDeclarations() throws JellyTagException {
    return getAnnotationProcessorEnvironment().getTypeDeclarations();
  }

  /**
   * Whether to include interfaces in the collection of types over which to iterate.
   *
   * @return Whether to include interfaces in the collection of types over which to iterate.
   */
  public boolean isIncludeInterfaces() {
    return includeInterfaces;
  }

  /**
   * Whether to include interfaces in the collection of types over which to iterate.
   *
   * @param includeInterfaces Whether to include interfaces in the collection of types over which to iterate.
   */
  public void setIncludeInterfaces(boolean includeInterfaces) {
    this.includeInterfaces = includeInterfaces;
  }

  /**
   * Whether to include classes in the collection of types over which to iterate.
   *
   * @return Whether to include classes in the collection of types over which to iterate.
   */
  public boolean isIncludeClasses() {
    return includeClasses;
  }

  /**
   * Whether to include classes in the collection of types over which to iterate.
   *
   * @param includeClasses Whether to include classes in the collection of types over which to iterate.
   */
  public void setIncludeClasses(boolean includeClasses) {
    this.includeClasses = includeClasses;
  }

}
