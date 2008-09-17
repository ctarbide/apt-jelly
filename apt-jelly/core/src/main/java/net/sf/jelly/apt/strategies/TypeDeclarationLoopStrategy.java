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

import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.declaration.InterfaceDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.PackageDeclaration;
import net.sf.jelly.apt.Context;
import net.sf.jelly.apt.TemplateBlock;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Evaluates its body for each {@link TypeDeclaration type declaration}.
 * <p/>
 * If the package is specified, only the top-level classes are returned.
 * <p/>
 * Unless specified otherwise, {@link ClassDeclaration}s will be included and {@link InterfaceDeclaration}s will NOT be included.
 * 
 * @author Ryan Heaton
 */
public class TypeDeclarationLoopStrategy<B extends TemplateBlock> extends AnnotationFilterableDeclarationLoopStrategy<TypeDeclaration, B> {

  private PackageDeclaration pckg;
  private boolean includeClasses = true;
  private boolean includeInterfaces = false;

  /**
   * The base collection of types over which to iterate.
   *
   * @return The base collection of types over which to iterate.
   */
  public Collection<TypeDeclaration> getAllDeclarationsToConsiderForAnnotationFiltering() throws MissingParameterException {
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
  protected Collection<TypeDeclaration> getAllTypeDeclarations() throws MissingParameterException {
    PackageDeclaration pckg = getPackage();
    if (pckg == null) {
      pckg = getCurrentPackageDeclaration();
    }

    if (pckg != null) {
      ArrayList<TypeDeclaration> typeDeclarations = new ArrayList<TypeDeclaration>(pckg.getClasses());
      typeDeclarations.addAll(pckg.getInterfaces());
      return typeDeclarations;
    }

    return getAnnotationProcessorEnvironment().getTypeDeclarations();
  }

  /**
   * The current annotation processor environment.
   *
   * @return The current annotation processor environment.
   */
  protected AnnotationProcessorEnvironment getAnnotationProcessorEnvironment() {
    return Context.getCurrentEnvironment();
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

  /**
   * The package declaration containing the types.
   *
   * @return The package declaration containing the types.
   */
  public PackageDeclaration getPackage() {
    return pckg;
  }

  /**
   * The package declaration containing the types.
   *
   * @param pckg The package declaration containing the types.
   */
  public void setPackage(PackageDeclaration pckg) {
    this.pckg = pckg;
  }

  //Inherited
  @Override
  public TypeDeclaration getCurrentDeclaration() {
    return super.getCurrentDeclaration();
  }

  /**
   * Gets the current declaration (in a loop).
   *
   * @return the current declaration (in a loop).
   */
  protected PackageDeclaration getCurrentPackageDeclaration() {
    PackageDeclarationLoopStrategy loop = StrategyStack.get().findFirst(PackageDeclarationLoopStrategy.class);
    if (loop != null) {
      return (PackageDeclaration) loop.getCurrentDeclaration();
    }
    return null;
  }
}
