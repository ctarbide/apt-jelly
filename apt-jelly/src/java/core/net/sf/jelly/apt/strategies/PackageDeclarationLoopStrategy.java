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
import com.sun.mirror.declaration.PackageDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import net.sf.jelly.apt.Context;

import java.util.Collection;
import java.util.HashMap;

/**
 * Evaluates its body for all packages.
 * 
 * @author Ryan Heaton
 */
public class PackageDeclarationLoopStrategy extends AnnotationFilterableDeclarationLoopStrategy<PackageDeclaration> {

  public Collection<PackageDeclaration> getAllDeclarationsToConsiderForAnnotationFiltering() throws MissingParameterException {
    return getAllPackageDeclarations(getAnnotationProcessorEnvironment());
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
   * Method for getting all package declarations of a given environment.
   *
   * @param env The environment.
   * @return All package declarations of a given environment.
   */
  public static Collection<PackageDeclaration> getAllPackageDeclarations(AnnotationProcessorEnvironment env) {
    HashMap<String, PackageDeclaration> packages = new HashMap<String, PackageDeclaration>();
    Collection<TypeDeclaration> typeDeclarations = env.getTypeDeclarations();
    for (TypeDeclaration typeDeclaration : typeDeclarations) {
      PackageDeclaration packageDecl = typeDeclaration.getPackage();
      packages.put(packageDecl.getQualifiedName(), packageDecl);
    }
    return packages.values();
  }

}
